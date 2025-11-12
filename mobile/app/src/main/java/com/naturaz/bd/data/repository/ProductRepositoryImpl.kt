package com.naturaz.bd.data.repository

import com.naturaz.bd.core.common.NaturazResult
import com.naturaz.bd.core.common.DispatcherProvider
import com.naturaz.bd.core.network.NetworkMonitor
import com.naturaz.bd.core.network.safeApiCall
import com.naturaz.bd.data.local.db.NaturazDatabase
import com.naturaz.bd.data.local.db.entity.HomeFeedEntity
import com.naturaz.bd.data.mapper.toDomain
import com.naturaz.bd.data.mapper.toEntity
import com.naturaz.bd.data.remote.api.ProductApi
import com.naturaz.bd.domain.model.Category
import com.naturaz.bd.domain.model.HomeFeed
import com.naturaz.bd.domain.model.Product
import com.naturaz.bd.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val database: NaturazDatabase,
    private val dispatchers: DispatcherProvider,
    private val networkMonitor: NetworkMonitor
) : ProductRepository {

    private val productDao = database.productDao()
    private val categoryDao = database.categoryDao()
    private val homeFeedDao = database.homeFeedDao()

    override fun observeHomeFeed(): Flow<NaturazResult<HomeFeed>> =
        combine(
            homeFeedDao.observeHomeFeed(),
            productDao.observeProducts(),
            categoryDao.observeCategories()
        ) { feedEntity, productEntities, categoryEntities ->
            if (feedEntity == null) {
                NaturazResult.Loading
            } else {
                val productMap = productEntities.associateBy { it.id }.mapValues { it.value.toDomain() }
                NaturazResult.Success(
                    HomeFeed(
                        heroBanners = feedEntity.heroBanners,
                        categories = categoryEntities.map { it.toDomain() },
                        featuredProducts = feedEntity.featuredProductIds.mapNotNull { productMap[it] },
                        ecoHighlights = feedEntity.ecoHighlightIds.mapNotNull { productMap[it] },
                        flashDeals = feedEntity.flashDealIds.mapNotNull { productMap[it] },
                        recommendations = feedEntity.recommendationIds.mapNotNull { productMap[it] }
                    )
                )
            }
        }.onStart { emit(NaturazResult.Loading) }

    override suspend fun refreshHomeFeed(force: Boolean): NaturazResult<HomeFeed> = withContext(dispatchers.io) {
        if (!networkMonitor.isConnected() && !force) {
            val feedEntity = homeFeedDao.observeHomeFeed().firstOrNull()
            val products = productDao.observeProducts().firstOrNull() ?: emptyList()
            val categories = categoryDao.observeCategories().firstOrNull() ?: emptyList()
            if (feedEntity != null && products.isNotEmpty()) {
                val productMap = products.associateBy { it.id }.mapValues { it.value.toDomain() }
                return@withContext NaturazResult.Success(
                    HomeFeed(
                        heroBanners = feedEntity.heroBanners,
                        categories = categories.map { it.toDomain() },
                        featuredProducts = feedEntity.featuredProductIds.mapNotNull { productMap[it] },
                        ecoHighlights = feedEntity.ecoHighlightIds.mapNotNull { productMap[it] },
                        flashDeals = feedEntity.flashDealIds.mapNotNull { productMap[it] },
                        recommendations = feedEntity.recommendationIds.mapNotNull { productMap[it] }
                    )
                )
            }
        }
        when (val result = safeApiCall { productApi.getHomeFeed() }) {
            is NaturazResult.Success -> {
                val dto = result.data
                val productEntities = dto.featuredProducts.plus(dto.ecoHighlights).plus(dto.flashDeals).plus(dto.recommendations)
                    .distinctBy { it.id }
                    .map { it.toEntity() }
                val categoryEntities = dto.categories.map { it.toEntity() }
                database.runInTransaction {
                    productDao.insertProducts(productEntities)
                    categoryDao.insertCategories(categoryEntities)
                    homeFeedDao.insertHomeFeed(
                        HomeFeedEntity(
                            heroBanners = dto.heroBanners,
                            featuredProductIds = dto.featuredProducts.map { it.id },
                            ecoHighlightIds = dto.ecoHighlights.map { it.id },
                            flashDealIds = dto.flashDeals.map { it.id },
                            recommendationIds = dto.recommendations.map { it.id },
                            updatedAt = System.currentTimeMillis()
                        )
                    )
                }
                val homeFeed = HomeFeed(
                    heroBanners = dto.heroBanners,
                    categories = categoryEntities.map { it.toDomain() },
                    featuredProducts = dto.featuredProducts.map { it.toDomain() },
                    ecoHighlights = dto.ecoHighlights.map { it.toDomain() },
                    flashDeals = dto.flashDeals.map { it.toDomain() },
                    recommendations = dto.recommendations.map { it.toDomain() }
                )
                NaturazResult.Success(homeFeed)
            }
            is NaturazResult.Error -> result
            NaturazResult.Loading -> NaturazResult.Loading
        }
    }

    override fun observeCategories(): Flow<List<Category>> =
        categoryDao.observeCategories().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getProductsByCategory(categoryId: String, page: Int): NaturazResult<List<Product>> =
        withContext(dispatchers.io) {
            when (val result = safeApiCall { productApi.getProductsByCategory(categoryId, page) }) {
                is NaturazResult.Success -> {
                    val entities = result.data.map { it.toEntity() }
                    productDao.insertProducts(entities)
                    NaturazResult.Success(result.data.map { it.toDomain() })
                }
                is NaturazResult.Error -> result
                NaturazResult.Loading -> NaturazResult.Loading
            }
        }

    override suspend fun searchProducts(query: String, filters: Map<String, String>, page: Int): NaturazResult<List<Product>> =
        withContext(dispatchers.io) {
            val filterQuery = if (filters.isEmpty()) null else filters.entries.joinToString(",") { "${it.key}:${it.value}" }
            when (val result = safeApiCall { productApi.searchProducts(query, filterQuery, page) }) {
                is NaturazResult.Success -> NaturazResult.Success(result.data.map { it.toDomain() })
                is NaturazResult.Error -> result
                NaturazResult.Loading -> NaturazResult.Loading
            }
        }

    override suspend fun getProductDetails(productId: String): NaturazResult<Product> = withContext(dispatchers.io) {
        val cached = productDao.getProductById(productId)?.toDomain()
        if (cached != null && !networkMonitor.isConnected()) {
            return@withContext NaturazResult.Success(cached)
        }
        when (val result = safeApiCall { productApi.getProductDetails(productId) }) {
            is NaturazResult.Success -> {
                val entity = result.data.toEntity()
                productDao.insertProducts(listOf(entity))
                NaturazResult.Success(result.data.toDomain())
            }
            is NaturazResult.Error -> {
                if (cached != null) NaturazResult.Success(cached) else result
            }
            NaturazResult.Loading -> NaturazResult.Loading
        }
    }
}

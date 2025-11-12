package com.naturaz.bd.domain.repository

import com.naturaz.bd.core.common.NaturazResult
import com.naturaz.bd.domain.model.Category
import com.naturaz.bd.domain.model.HomeFeed
import com.naturaz.bd.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeHomeFeed(): Flow<NaturazResult<HomeFeed>>
    suspend fun refreshHomeFeed(force: Boolean = false): NaturazResult<HomeFeed>
    fun observeCategories(): Flow<List<Category>>
    suspend fun getProductsByCategory(categoryId: String, page: Int): NaturazResult<List<Product>>
    suspend fun searchProducts(query: String, filters: Map<String, String>, page: Int): NaturazResult<List<Product>>
    suspend fun getProductDetails(productId: String): NaturazResult<Product>
}

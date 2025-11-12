package com.naturaz.bd.data.repository

import com.naturaz.bd.core.common.NaturazResult
import com.naturaz.bd.core.common.DispatcherProvider
import com.naturaz.bd.core.network.safeApiCall
import com.naturaz.bd.data.local.db.NaturazDatabase
import com.naturaz.bd.data.mapper.toDomain
import com.naturaz.bd.data.mapper.toEntity
import com.naturaz.bd.data.remote.api.CartApi
import com.naturaz.bd.data.remote.api.CartItemQuantityRequest
import com.naturaz.bd.data.remote.api.CartItemUpdateRequest
import com.naturaz.bd.domain.model.CartItem
import com.naturaz.bd.domain.repository.CartRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartApi: CartApi,
    private val database: NaturazDatabase,
    private val dispatchers: DispatcherProvider
) : CartRepository {

    private val cartDao = database.cartDao()
    private val productDao = database.productDao()

    override fun observeCart(): Flow<List<CartItem>> =
        combine(cartDao.observeCartItems(), productDao.observeProducts()) { cartItems, products ->
            val productMap = products.associateBy { it.id }.mapValues { it.value.toDomain() }
            cartItems.mapNotNull { entity ->
                val product = productMap[entity.productId]
                product?.let { entity.toDomain(it) }
            }
        }

    override suspend fun syncCart(): NaturazResult<Unit> = withContext(dispatchers.io) {
        when (val result = safeApiCall { cartApi.getCart() }) {
            is NaturazResult.Success -> {
                val items = result.data.items
                cartDao.replaceCart(items.map { it.toEntity() })
                NaturazResult.Success(Unit)
            }
            is NaturazResult.Error -> result
            NaturazResult.Loading -> NaturazResult.Loading
        }
    }

    override suspend fun addToCart(productId: String, quantity: Int, notes: String?): NaturazResult<Unit> =
        withContext(dispatchers.io) {
            when (val result = safeApiCall { cartApi.addToCart(CartItemUpdateRequest(productId, quantity, notes)) }) {
                is NaturazResult.Success -> {
                    cartDao.replaceCart(result.data.items.map { it.toEntity() })
                    NaturazResult.Success(Unit)
                }
                is NaturazResult.Error -> result
                NaturazResult.Loading -> NaturazResult.Loading
            }
        }

    override suspend fun updateCartItem(itemId: String, quantity: Int): NaturazResult<Unit> =
        withContext(dispatchers.io) {
            when (val result = safeApiCall { cartApi.updateCartItem(itemId, CartItemQuantityRequest(quantity)) }) {
                is NaturazResult.Success -> {
                    val entity = result.data.toEntity()
                    cartDao.insertOrReplace(listOf(entity))
                    NaturazResult.Success(Unit)
                }
                is NaturazResult.Error -> result
                NaturazResult.Loading -> NaturazResult.Loading
            }
        }

    override suspend fun removeCartItem(itemId: String): NaturazResult<Unit> = withContext(dispatchers.io) {
        when (val result = safeApiCall { cartApi.removeCartItem(itemId) }) {
            is NaturazResult.Success -> {
                syncCart()
            }
            is NaturazResult.Error -> result
            NaturazResult.Loading -> NaturazResult.Loading
        }
    }

    override suspend fun clearCart(): NaturazResult<Unit> = withContext(dispatchers.io) {
        when (val result = safeApiCall { cartApi.clearCart() }) {
            is NaturazResult.Success -> {
                cartDao.clear()
                NaturazResult.Success(Unit)
            }
            is NaturazResult.Error -> result
            NaturazResult.Loading -> NaturazResult.Loading
        }
    }
}

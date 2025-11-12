package com.naturaz.bd.domain.repository

import com.naturaz.bd.core.common.NaturazResult
import com.naturaz.bd.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCart(): Flow<List<CartItem>>
    suspend fun syncCart(): NaturazResult<Unit>
    suspend fun addToCart(productId: String, quantity: Int, notes: String? = null): NaturazResult<Unit>
    suspend fun updateCartItem(itemId: String, quantity: Int): NaturazResult<Unit>
    suspend fun removeCartItem(itemId: String): NaturazResult<Unit>
    suspend fun clearCart(): NaturazResult<Unit>
}

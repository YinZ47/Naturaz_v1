package com.naturaz.bd.domain.repository

import com.naturaz.bd.core.common.NaturazResult
import com.naturaz.bd.domain.model.Order
import com.naturaz.bd.domain.model.OrderStatus
import com.naturaz.bd.domain.model.ShippingAddress
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun observeOrders(): Flow<List<Order>>
    suspend fun getOrder(orderId: String): NaturazResult<Order>
    suspend fun placeOrder(paymentMethodId: String, address: ShippingAddress): NaturazResult<Order>
    suspend fun cancelOrder(orderId: String, reason: String): NaturazResult<Unit>
    suspend fun reorder(orderId: String): NaturazResult<Order>
    suspend fun trackOrder(orderId: String): Flow<NaturazResult<OrderStatus>>
}

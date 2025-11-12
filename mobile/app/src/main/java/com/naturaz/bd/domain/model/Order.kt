package com.naturaz.bd.domain.model

import java.time.Instant

data class Order(
    val id: String,
    val items: List<CartItem>,
    val status: OrderStatus,
    val totalAmount: Double,
    val currency: Currency,
    val placedAt: Instant,
    val expectedDelivery: Instant?,
    val trackingCode: String?,
    val address: ShippingAddress,
    val paymentMethod: PaymentMethod
)

data class ShippingAddress(
    val name: String,
    val phone: String,
    val division: String,
    val district: String,
    val thana: String,
    val streetAddress: String,
    val postalCode: String,
    val latitude: Double?,
    val longitude: Double?
)

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    PROCESSING,
    SHIPPED,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED,
    RETURN_REQUESTED,
    RETURNED
}

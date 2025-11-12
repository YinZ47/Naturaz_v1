package com.naturaz.bd.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShippingAddressDto(
    @SerialName("name") val name: String,
    @SerialName("phone") val phone: String,
    @SerialName("division") val division: String,
    @SerialName("district") val district: String,
    @SerialName("thana") val thana: String,
    @SerialName("street_address") val streetAddress: String,
    @SerialName("postal_code") val postalCode: String,
    @SerialName("latitude") val latitude: Double? = null,
    @SerialName("longitude") val longitude: Double? = null
)

@Serializable
data class OrderDto(
    @SerialName("id") val id: String,
    @SerialName("items") val items: List<CartItemDto>,
    @SerialName("status") val status: String,
    @SerialName("total_amount") val totalAmount: Double,
    @SerialName("currency") val currency: String,
    @SerialName("placed_at") val placedAt: String,
    @SerialName("expected_delivery") val expectedDelivery: String? = null,
    @SerialName("tracking_code") val trackingCode: String? = null,
    @SerialName("address") val address: ShippingAddressDto,
    @SerialName("payment_method") val paymentMethod: String
)

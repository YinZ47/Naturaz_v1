package com.naturaz.bd.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItemDto(
    @SerialName("id") val id: String,
    @SerialName("product") val product: ProductDto,
    @SerialName("quantity") val quantity: Int,
    @SerialName("total_price") val totalPrice: Double,
    @SerialName("notes") val notes: String? = null
)

@Serializable
data class CartResponseDto(
    @SerialName("items") val items: List<CartItemDto> = emptyList()
)

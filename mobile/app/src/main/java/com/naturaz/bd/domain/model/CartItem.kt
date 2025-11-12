package com.naturaz.bd.domain.model

data class CartItem(
    val id: String,
    val product: Product,
    val quantity: Int,
    val totalPrice: Double,
    val notes: String?
)

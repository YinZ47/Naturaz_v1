package com.naturaz.bd.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val id: String,
    val productId: String,
    val quantity: Int,
    val totalPrice: Double,
    val notes: String?
)

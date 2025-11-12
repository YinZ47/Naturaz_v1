package com.naturaz.bd.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val brand: String,
    val price: Double,
    val discountedPrice: Double?,
    val currencyCode: String,
    val imageUrls: List<String>,
    val ecoScore: Int,
    val ecoCertifications: List<String>,
    val carbonFootprintKg: Double,
    val ecoSummary: String,
    val rating: Double,
    val totalReviews: Int,
    val inStock: Boolean,
    val stockCount: Int,
    val categoryId: String,
    val categoryName: String,
    val vendorId: String,
    val vendorName: String,
    val badges: List<String>
)

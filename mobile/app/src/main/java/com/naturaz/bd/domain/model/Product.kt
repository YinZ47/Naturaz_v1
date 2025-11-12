package com.naturaz.bd.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val brand: String,
    val price: Double,
    val discountedPrice: Double?,
    val currency: Currency,
    val images: List<String>,
    val ecoScore: EcoScore,
    val rating: Double,
    val totalReviews: Int,
    val inStock: Boolean,
    val stockCount: Int,
    val category: Category,
    val vendorId: String,
    val vendorName: String,
    val badges: List<String>
)

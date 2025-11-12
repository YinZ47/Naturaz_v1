package com.naturaz.bd.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("brand") val brand: String,
    @SerialName("price") val price: Double,
    @SerialName("discounted_price") val discountedPrice: Double? = null,
    @SerialName("currency") val currency: String = "BDT",
    @SerialName("image_urls") val imageUrls: List<String> = emptyList(),
    @SerialName("eco_score") val ecoScore: EcoScoreDto,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("total_reviews") val totalReviews: Int = 0,
    @SerialName("in_stock") val inStock: Boolean = true,
    @SerialName("stock_count") val stockCount: Int = 0,
    @SerialName("category") val category: CategoryDto,
    @SerialName("vendor_id") val vendorId: String,
    @SerialName("vendor_name") val vendorName: String,
    @SerialName("badges") val badges: List<String> = emptyList()
)

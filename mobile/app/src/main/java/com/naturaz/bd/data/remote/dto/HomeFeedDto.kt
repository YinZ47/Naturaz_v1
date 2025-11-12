package com.naturaz.bd.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeFeedDto(
    @SerialName("hero_banners") val heroBanners: List<String> = emptyList(),
    @SerialName("categories") val categories: List<CategoryDto> = emptyList(),
    @SerialName("featured_products") val featuredProducts: List<ProductDto> = emptyList(),
    @SerialName("eco_highlights") val ecoHighlights: List<ProductDto> = emptyList(),
    @SerialName("flash_deals") val flashDeals: List<ProductDto> = emptyList(),
    @SerialName("recommendations") val recommendations: List<ProductDto> = emptyList()
)

package com.naturaz.bd.domain.model

data class HomeFeed(
    val heroBanners: List<String>,
    val categories: List<Category>,
    val featuredProducts: List<Product>,
    val ecoHighlights: List<Product>,
    val flashDeals: List<Product>,
    val recommendations: List<Product>
)

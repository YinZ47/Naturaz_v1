package com.naturaz.bd.data.remote.api

import com.naturaz.bd.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RecommendationApi {
    @GET("api/v1/recommendations/personalized")
    suspend fun getPersonalized(): List<ProductDto>

    @GET("api/v1/recommendations/similar/{productId}")
    suspend fun getSimilar(@Path("productId") productId: String): List<ProductDto>
}

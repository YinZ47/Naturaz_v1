package com.naturaz.bd.data.remote.api

import com.naturaz.bd.data.remote.dto.CategoryDto
import com.naturaz.bd.data.remote.dto.HomeFeedDto
import com.naturaz.bd.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("api/v1/products/home")
    suspend fun getHomeFeed(): HomeFeedDto

    @GET("api/v1/products/categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("api/v1/products/category/{categoryId}")
    suspend fun getProductsByCategory(
        @Path("categoryId") categoryId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): List<ProductDto>

    @GET("api/v1/products/{productId}")
    suspend fun getProductDetails(@Path("productId") productId: String): ProductDto

    @GET("api/v1/search")
    suspend fun searchProducts(
        @Query("query") query: String,
        @Query("filters") filters: String?,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): List<ProductDto>
}

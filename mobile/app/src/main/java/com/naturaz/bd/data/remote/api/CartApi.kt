package com.naturaz.bd.data.remote.api

import com.naturaz.bd.data.remote.dto.CartItemDto
import com.naturaz.bd.data.remote.dto.CartResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartApi {
    @GET("api/v1/cart")
    suspend fun getCart(): CartResponseDto

    @POST("api/v1/cart")
    suspend fun addToCart(@Body body: CartItemUpdateRequest): CartResponseDto

    @PUT("api/v1/cart/{itemId}")
    suspend fun updateCartItem(
        @Path("itemId") itemId: String,
        @Body body: CartItemQuantityRequest
    ): CartItemDto

    @DELETE("api/v1/cart/{itemId}")
    suspend fun removeCartItem(@Path("itemId") itemId: String)

    @DELETE("api/v1/cart")
    suspend fun clearCart()
}

@kotlinx.serialization.Serializable
data class CartItemUpdateRequest(
    val productId: String,
    val quantity: Int,
    val notes: String? = null
)

@kotlinx.serialization.Serializable
data class CartItemQuantityRequest(
    val quantity: Int
)

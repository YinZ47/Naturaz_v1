package com.naturaz.bd.data.remote.api

import com.naturaz.bd.data.remote.dto.OrderDto
import com.naturaz.bd.data.remote.dto.ShippingAddressDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApi {
    @GET("api/v1/orders")
    suspend fun getOrders(): List<OrderDto>

    @GET("api/v1/orders/{orderId}")
    suspend fun getOrder(@Path("orderId") orderId: String): OrderDto

    @POST("api/v1/orders")
    suspend fun placeOrder(@Body request: PlaceOrderRequest): OrderDto

    @PUT("api/v1/orders/{orderId}/cancel")
    suspend fun cancelOrder(
        @Path("orderId") orderId: String,
        @Body request: CancelOrderRequest
    )
}

@Serializable
data class PlaceOrderRequest(
    @SerialName("payment_method_id") val paymentMethodId: String,
    @SerialName("address") val address: ShippingAddressDto
)

@Serializable
data class CancelOrderRequest(
    @SerialName("reason") val reason: String
)

package com.naturaz.bd.data.remote.api

import com.naturaz.bd.data.remote.dto.PaymentIntentDto
import com.naturaz.bd.data.remote.dto.PaymentMethodDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PaymentApi {
    @GET("api/v1/payments/methods")
    suspend fun getPaymentMethods(): List<PaymentMethodDto>

    @POST("api/v1/payments/bkash")
    suspend fun initiateBkash(@Body request: PaymentInitiationRequest): PaymentIntentDto

    @POST("api/v1/payments/nagad")
    suspend fun initiateNagad(@Body request: PaymentInitiationRequest): PaymentIntentDto

    @POST("api/v1/payments/rocket")
    suspend fun initiateRocket(@Body request: PaymentInitiationRequest): PaymentIntentDto

    @POST("api/v1/payments/verify")
    suspend fun verifyPayment(@Body request: VerifyPaymentRequest)
}

@Serializable
data class PaymentInitiationRequest(
    @SerialName("order_id") val orderId: String,
    @SerialName("amount") val amount: Double
)

@Serializable
data class VerifyPaymentRequest(
    @SerialName("transaction_id") val transactionId: String
)

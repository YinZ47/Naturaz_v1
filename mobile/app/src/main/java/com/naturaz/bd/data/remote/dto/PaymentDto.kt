package com.naturaz.bd.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentMethodDto(
    @SerialName("type") val type: String,
    @SerialName("provider") val provider: String? = null,
    @SerialName("masked_pan") val maskedPan: String? = null,
    @SerialName("card_holder") val cardHolder: String? = null,
    @SerialName("account_number") val accountNumber: String? = null,
    @SerialName("instructions") val instructions: String? = null
)

@Serializable
data class PaymentIntentDto(
    @SerialName("redirect_url") val redirectUrl: String? = null,
    @SerialName("transaction_id") val transactionId: String,
    @SerialName("expires_at") val expiresAt: String? = null
)

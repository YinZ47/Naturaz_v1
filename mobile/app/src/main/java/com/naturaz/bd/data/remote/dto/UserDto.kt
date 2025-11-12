package com.naturaz.bd.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("email") val email: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("eco_points") val ecoPoints: Int = 0,
    @SerialName("locale") val locale: String = "en",
    @SerialName("preferred_currency") val preferredCurrency: String = "BDT",
    @SerialName("notification_enabled") val notificationEnabled: Boolean = true,
    @SerialName("biometric_enabled") val biometricEnabled: Boolean = false
)

package com.naturaz.bd.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmailLoginRequestDto(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)

@Serializable
data class PhoneLoginRequestDto(
    @SerialName("phone") val phone: String,
    @SerialName("otp") val otp: String
)

@Serializable
data class SocialLoginRequestDto(
    @SerialName("provider") val provider: String,
    @SerialName("token") val token: String
)

@Serializable
data class RegisterRequestDto(
    @SerialName("full_name") val fullName: String,
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String,
    @SerialName("password") val password: String
)

@Serializable
data class AuthResponseDto(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("expires_in") val expiresIn: Long
)

package com.naturaz.bd.data.remote.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationApi {
    @POST("api/v1/notifications/register")
    suspend fun registerFcmToken(@Body request: FcmTokenRequest)

    @POST("api/v1/notifications/preferences")
    suspend fun updateNotificationPreference(@Body request: NotificationPreferenceRequest)
}

@Serializable
data class FcmTokenRequest(
    @SerialName("token") val token: String
)

@Serializable
data class NotificationPreferenceRequest(
    @SerialName("enabled") val enabled: Boolean
)

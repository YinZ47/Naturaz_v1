package com.naturaz.bd.domain.repository

import com.naturaz.bd.core.common.NaturazResult
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun observeNotifications(): Flow<NaturazResult<Unit>>
    suspend fun updateFcmToken(token: String): NaturazResult<Unit>
    suspend fun updateNotificationPreference(enabled: Boolean): NaturazResult<Unit>
}

package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.NotificationRepository
import javax.inject.Inject

class UpdateNotificationPreferenceUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(enabled: Boolean) =
        notificationRepository.updateNotificationPreference(enabled)
}

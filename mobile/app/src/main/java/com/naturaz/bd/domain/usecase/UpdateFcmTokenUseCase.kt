package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.NotificationRepository
import javax.inject.Inject

class UpdateFcmTokenUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(token: String) =
        notificationRepository.updateFcmToken(token)
}

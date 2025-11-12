package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.AuthRepository
import javax.inject.Inject

class ObserveAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.observeAccessToken()
}

package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.AuthRepository
import javax.inject.Inject

class LoginWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        authRepository.loginWithEmail(email, password)
}

package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.AuthRepository
import javax.inject.Inject

class LoginWithPhoneUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phone: String, otp: String) =
        authRepository.loginWithPhone(phone, otp)
}

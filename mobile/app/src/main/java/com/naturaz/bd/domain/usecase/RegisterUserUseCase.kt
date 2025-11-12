package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        fullName: String,
        email: String,
        phone: String,
        password: String
    ) = authRepository.registerUser(fullName, email, phone, password)
}

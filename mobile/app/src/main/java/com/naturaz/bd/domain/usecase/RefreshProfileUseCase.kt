package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.UserRepository
import javax.inject.Inject

class RefreshProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.refreshProfile()
}

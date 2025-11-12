package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.model.User
import com.naturaz.bd.domain.repository.UserRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) = userRepository.updateProfile(user)
}

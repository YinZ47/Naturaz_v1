package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.UserRepository
import javax.inject.Inject

class ObserveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.observeUser()
}

package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.CartRepository
import javax.inject.Inject

class ObserveCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke() = cartRepository.observeCart()
}

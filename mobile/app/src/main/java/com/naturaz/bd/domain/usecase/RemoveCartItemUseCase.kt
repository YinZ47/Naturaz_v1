package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.CartRepository
import javax.inject.Inject

class RemoveCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(itemId: String) = cartRepository.removeCartItem(itemId)
}

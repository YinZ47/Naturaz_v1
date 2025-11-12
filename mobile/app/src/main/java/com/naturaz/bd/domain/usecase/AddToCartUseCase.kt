package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: String, quantity: Int, notes: String? = null) =
        cartRepository.addToCart(productId, quantity, notes)
}

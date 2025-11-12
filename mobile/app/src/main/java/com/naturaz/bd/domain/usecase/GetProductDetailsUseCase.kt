package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: String) =
        productRepository.getProductDetails(productId)
}

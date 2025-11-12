package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(categoryId: String, page: Int) =
        productRepository.getProductsByCategory(categoryId, page)
}

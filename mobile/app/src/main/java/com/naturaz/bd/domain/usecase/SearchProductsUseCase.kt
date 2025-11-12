package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.ProductRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(query: String, filters: Map<String, String>, page: Int) =
        productRepository.searchProducts(query, filters, page)
}

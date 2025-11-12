package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.RecommendationRepository
import javax.inject.Inject

class GetSimilarProductsUseCase @Inject constructor(
    private val recommendationRepository: RecommendationRepository
) {
    suspend operator fun invoke(productId: String) =
        recommendationRepository.getSimilarProducts(productId)
}

package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.ProductRepository
import javax.inject.Inject

class ObserveHomeFeedUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke() = productRepository.observeHomeFeed()
}

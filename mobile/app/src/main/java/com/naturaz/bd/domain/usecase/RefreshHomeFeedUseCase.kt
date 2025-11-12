package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.ProductRepository
import javax.inject.Inject

class RefreshHomeFeedUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(force: Boolean = false) =
        productRepository.refreshHomeFeed(force)
}

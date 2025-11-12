package com.naturaz.bd.domain.repository

import com.naturaz.bd.core.common.NaturazResult
import com.naturaz.bd.domain.model.Product

interface RecommendationRepository {
    suspend fun getPersonalizedFeed(): NaturazResult<List<Product>>
    suspend fun getSimilarProducts(productId: String): NaturazResult<List<Product>>
}

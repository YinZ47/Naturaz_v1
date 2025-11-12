package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.OrderRepository
import javax.inject.Inject

class TrackOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(orderId: String) = orderRepository.trackOrder(orderId)
}

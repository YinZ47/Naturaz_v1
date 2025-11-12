package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.OrderRepository
import javax.inject.Inject

class ObserveOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke() = orderRepository.observeOrders()
}

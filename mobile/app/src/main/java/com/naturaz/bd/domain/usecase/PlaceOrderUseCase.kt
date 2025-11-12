package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.model.ShippingAddress
import com.naturaz.bd.domain.repository.OrderRepository
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(paymentMethodId: String, address: ShippingAddress) =
        orderRepository.placeOrder(paymentMethodId, address)
}

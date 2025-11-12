package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.PaymentRepository
import javax.inject.Inject

class InitiateBkashPaymentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(amount: Double, orderId: String) =
        paymentRepository.initiateBkashPayment(amount, orderId)
}

package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.PaymentRepository
import javax.inject.Inject

class VerifyPaymentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(transactionId: String) =
        paymentRepository.verifyPayment(transactionId)
}

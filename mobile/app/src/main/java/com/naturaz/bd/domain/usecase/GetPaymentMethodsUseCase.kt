package com.naturaz.bd.domain.usecase

import com.naturaz.bd.domain.repository.PaymentRepository
import javax.inject.Inject

class GetPaymentMethodsUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke() = paymentRepository.getPaymentMethods()
}

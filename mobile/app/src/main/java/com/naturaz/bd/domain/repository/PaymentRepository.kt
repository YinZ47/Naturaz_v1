package com.naturaz.bd.domain.repository

import com.naturaz.bd.core.common.NaturazResult
import com.naturaz.bd.domain.model.PaymentMethod

interface PaymentRepository {
    suspend fun getPaymentMethods(): NaturazResult<List<PaymentMethod>>
    suspend fun initiateBkashPayment(amount: Double, orderId: String): NaturazResult<String>
    suspend fun initiateNagadPayment(amount: Double, orderId: String): NaturazResult<String>
    suspend fun initiateRocketPayment(amount: Double, orderId: String): NaturazResult<String>
    suspend fun verifyPayment(transactionId: String): NaturazResult<Unit>
}

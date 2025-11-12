package com.naturaz.bd.data.mapper

import com.naturaz.bd.data.remote.dto.PaymentIntentDto
import com.naturaz.bd.data.remote.dto.PaymentMethodDto
import com.naturaz.bd.domain.model.PaymentMethod
import com.naturaz.bd.domain.model.MobileWalletProvider

fun PaymentMethodDto.toDomain(): PaymentMethod = when (type.uppercase()) {
    "MOBILE_WALLET" -> PaymentMethod.MobileWallet(
        provider = provider.toMobileWalletProvider(),
        accountNumber = accountNumber
    )
    "CARD" -> PaymentMethod.Card(
        maskedPan = maskedPan.orEmpty(),
        cardHolder = cardHolder.orEmpty()
    )
    "COD" -> PaymentMethod.CashOnDelivery(instructions.orEmpty())
    "BANK" -> PaymentMethod.BankTransfer(
        bankName = provider ?: "Unknown",
        reference = instructions.orEmpty()
    )
    else -> PaymentMethod.CashOnDelivery(instructions.orEmpty())
}

fun PaymentIntentDto.toPaymentUrl(): String = redirectUrl ?: ""

private fun String?.toMobileWalletProvider(): MobileWalletProvider = when (this?.uppercase()) {
    "BKASH" -> MobileWalletProvider.BKASH
    "NAGAD" -> MobileWalletProvider.NAGAD
    "ROCKET" -> MobileWalletProvider.ROCKET
    else -> MobileWalletProvider.BKASH
}

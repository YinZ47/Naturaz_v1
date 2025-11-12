package com.naturaz.bd.domain.model

sealed class PaymentMethod {
    data class MobileWallet(val provider: MobileWalletProvider, val accountNumber: String?) : PaymentMethod()
    data class Card(val maskedPan: String, val cardHolder: String) : PaymentMethod()
    data class CashOnDelivery(val instructions: String = "Pay during delivery") : PaymentMethod()
    data class BankTransfer(val bankName: String, val reference: String) : PaymentMethod()
}

enum class MobileWalletProvider {
    BKASH,
    NAGAD,
    ROCKET,
    UPAY
}

package com.naturaz.bd.data.mapper

import com.naturaz.bd.data.remote.dto.OrderDto
import com.naturaz.bd.data.remote.dto.ShippingAddressDto
import com.naturaz.bd.domain.model.CartItem
import com.naturaz.bd.domain.model.Currency
import com.naturaz.bd.domain.model.Order
import com.naturaz.bd.domain.model.OrderStatus
import com.naturaz.bd.domain.model.PaymentMethod
import com.naturaz.bd.domain.model.Product
import com.naturaz.bd.domain.model.ShippingAddress
import java.time.Instant

fun OrderDto.toDomain(products: Map<String, Product>): Order = Order(
    id = id,
    items = items.mapNotNull { itemDto ->
        val product = products[itemDto.product.id]
        product?.let { itemDto.toDomain(it) }
    },
    status = status.toOrderStatus(),
    totalAmount = totalAmount,
    currency = currency.toCurrency(),
    placedAt = Instant.parse(placedAt),
    expectedDelivery = expectedDelivery?.let(Instant::parse),
    trackingCode = trackingCode,
    address = address.toDomain(),
    paymentMethod = paymentMethod.toPaymentMethod()
)

fun ShippingAddressDto.toDomain(): ShippingAddress = ShippingAddress(
    name = name,
    phone = phone,
    division = division,
    district = district,
    thana = thana,
    streetAddress = streetAddress,
    postalCode = postalCode,
    latitude = latitude,
    longitude = longitude
)

private fun String.toOrderStatus(): OrderStatus = when (uppercase()) {
    "PENDING" -> OrderStatus.PENDING
    "CONFIRMED" -> OrderStatus.CONFIRMED
    "PROCESSING" -> OrderStatus.PROCESSING
    "SHIPPED" -> OrderStatus.SHIPPED
    "OUT_FOR_DELIVERY" -> OrderStatus.OUT_FOR_DELIVERY
    "DELIVERED" -> OrderStatus.DELIVERED
    "CANCELLED" -> OrderStatus.CANCELLED
    "RETURN_REQUESTED" -> OrderStatus.RETURN_REQUESTED
    "RETURNED" -> OrderStatus.RETURNED
    else -> OrderStatus.PENDING
}

private fun String.toPaymentMethod(): PaymentMethod = when (uppercase()) {
    "BKASH" -> PaymentMethod.MobileWallet(provider = com.naturaz.bd.domain.model.MobileWalletProvider.BKASH, accountNumber = null)
    "NAGAD" -> PaymentMethod.MobileWallet(provider = com.naturaz.bd.domain.model.MobileWalletProvider.NAGAD, accountNumber = null)
    "ROCKET" -> PaymentMethod.MobileWallet(provider = com.naturaz.bd.domain.model.MobileWalletProvider.ROCKET, accountNumber = null)
    "COD" -> PaymentMethod.CashOnDelivery()
    else -> PaymentMethod.CashOnDelivery()
}

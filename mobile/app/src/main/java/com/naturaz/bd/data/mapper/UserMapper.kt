package com.naturaz.bd.data.mapper

import com.naturaz.bd.data.remote.dto.UserDto
import com.naturaz.bd.domain.model.Currency
import com.naturaz.bd.domain.model.User

fun UserDto.toDomain(): User = User(
    id = id,
    fullName = fullName,
    email = email,
    phone = phone,
    avatarUrl = avatarUrl,
    ecoPoints = ecoPoints,
    locale = locale,
    preferredCurrency = preferredCurrency.toCurrency(),
    notificationEnabled = notificationEnabled,
    biometricEnabled = biometricEnabled
)

fun User.toDto(): UserDto = UserDto(
    id = id,
    fullName = fullName,
    email = email,
    phone = phone,
    avatarUrl = avatarUrl,
    ecoPoints = ecoPoints,
    locale = locale,
    preferredCurrency = preferredCurrency.code,
    notificationEnabled = notificationEnabled,
    biometricEnabled = biometricEnabled
)

val DEFAULT_CURRENCY = Currency.BDT

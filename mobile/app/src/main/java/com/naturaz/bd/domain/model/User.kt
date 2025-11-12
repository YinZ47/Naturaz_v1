package com.naturaz.bd.domain.model

data class User(
    val id: String,
    val fullName: String,
    val email: String?,
    val phone: String?,
    val avatarUrl: String?,
    val ecoPoints: Int,
    val locale: String,
    val preferredCurrency: Currency,
    val notificationEnabled: Boolean,
    val biometricEnabled: Boolean
)

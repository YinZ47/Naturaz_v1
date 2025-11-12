package com.naturaz.bd.domain.model

data class EcoScore(
    val score: Int,
    val certificationBadges: List<String>,
    val carbonFootprintKg: Double,
    val sustainabilitySummary: String
)

package com.naturaz.bd.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EcoScoreDto(
    @SerialName("score") val score: Int,
    @SerialName("certifications") val certifications: List<String> = emptyList(),
    @SerialName("carbon_footprint_kg") val carbonFootprintKg: Double = 0.0,
    @SerialName("summary") val summary: String = ""
)

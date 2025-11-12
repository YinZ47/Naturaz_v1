package com.naturaz.bd.data.mapper

import com.naturaz.bd.data.local.db.entity.ProductEntity
import com.naturaz.bd.data.remote.dto.EcoScoreDto
import com.naturaz.bd.domain.model.EcoScore

fun EcoScoreDto.toDomain(): EcoScore = EcoScore(
    score = score,
    certificationBadges = certifications,
    carbonFootprintKg = carbonFootprintKg,
    sustainabilitySummary = summary
)

fun EcoScore.toEntityFields(productEntity: ProductEntity): ProductEntity = productEntity.copy(
    ecoScore = score,
    ecoCertifications = certificationBadges,
    carbonFootprintKg = carbonFootprintKg,
    ecoSummary = sustainabilitySummary
)

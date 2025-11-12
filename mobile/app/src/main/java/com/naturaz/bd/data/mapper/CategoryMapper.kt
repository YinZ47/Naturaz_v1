package com.naturaz.bd.data.mapper

import com.naturaz.bd.data.local.db.entity.CategoryEntity
import com.naturaz.bd.data.remote.dto.CategoryDto
import com.naturaz.bd.domain.model.Category

fun CategoryDto.toEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    iconUrl = iconUrl,
    parentId = parentId,
    ecoFriendly = ecoFriendly
)

fun CategoryDto.toDomain(): Category = Category(
    id = id,
    name = name,
    iconUrl = iconUrl,
    parentId = parentId,
    ecoFriendly = ecoFriendly
)

fun CategoryEntity.toDomain(): Category = Category(
    id = id,
    name = name,
    iconUrl = iconUrl,
    parentId = parentId,
    ecoFriendly = ecoFriendly
)

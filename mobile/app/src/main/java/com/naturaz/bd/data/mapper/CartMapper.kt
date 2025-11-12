package com.naturaz.bd.data.mapper

import com.naturaz.bd.data.local.db.entity.CartItemEntity
import com.naturaz.bd.data.remote.dto.CartItemDto
import com.naturaz.bd.domain.model.CartItem
import com.naturaz.bd.domain.model.Product

fun CartItemDto.toEntity(): CartItemEntity = CartItemEntity(
    id = id,
    productId = product.id,
    quantity = quantity,
    totalPrice = totalPrice,
    notes = notes
)

fun CartItemEntity.toDomain(product: Product): CartItem = CartItem(
    id = id,
    product = product,
    quantity = quantity,
    totalPrice = totalPrice,
    notes = notes
)

fun CartItemDto.toDomain(product: Product): CartItem = CartItem(
    id = id,
    product = product,
    quantity = quantity,
    totalPrice = totalPrice,
    notes = notes
)

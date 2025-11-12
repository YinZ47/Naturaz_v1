package com.naturaz.bd.domain.model

data class Category(
    val id: String,
    val name: String,
    val iconUrl: String?,
    val parentId: String?,
    val ecoFriendly: Boolean
)

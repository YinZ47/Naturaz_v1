package com.naturaz.bd.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val iconUrl: String?,
    val parentId: String?,
    val ecoFriendly: Boolean
)

package com.naturaz.bd.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_feed")
data class HomeFeedEntity(
    @PrimaryKey val id: Int = 0,
    val heroBanners: List<String>,
    val featuredProductIds: List<String>,
    val ecoHighlightIds: List<String>,
    val flashDealIds: List<String>,
    val recommendationIds: List<String>,
    val updatedAt: Long
)

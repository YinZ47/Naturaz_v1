package com.naturaz.bd.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("icon_url") val iconUrl: String? = null,
    @SerialName("parent_id") val parentId: String? = null,
    @SerialName("eco_friendly") val ecoFriendly: Boolean = false
)

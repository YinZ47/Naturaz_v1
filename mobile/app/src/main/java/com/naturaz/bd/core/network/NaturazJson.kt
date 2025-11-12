package com.naturaz.bd.core.network

import kotlinx.serialization.json.Json

val NaturazJson = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    explicitNulls = false
    coerceInputValues = true
}

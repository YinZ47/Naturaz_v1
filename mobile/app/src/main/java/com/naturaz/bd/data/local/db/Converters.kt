package com.naturaz.bd.data.local.db

import androidx.room.TypeConverter
import com.naturaz.bd.core.network.NaturazJson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? = value?.let { NaturazJson.encodeToString(it) }

    @TypeConverter
    fun toStringList(value: String?): List<String> =
    value?.let { NaturazJson.decodeFromString<List<String>>(it) } ?: emptyList()

    @TypeConverter
    fun fromDoubleList(value: List<Double>?): String? = value?.let { NaturazJson.encodeToString(it) }

    @TypeConverter
    fun toDoubleList(value: String?): List<Double> =
        value?.let { NaturazJson.decodeFromString<List<Double>>(it) } ?: emptyList()
}

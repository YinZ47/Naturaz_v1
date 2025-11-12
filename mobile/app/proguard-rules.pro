# Keep data models for Retrofit and Room
-keep class com.naturaz.bd.data.remote.dto.** { *; }
-keep class com.naturaz.bd.domain.model.** { *; }

# Retrofit and OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep class kotlinx.serialization.** { *; }

# Hilt
-keep class dagger.hilt.internal.** { *; }
-dontwarn dagger.hilt.internal.**

# Room
-keep class androidx.room.paging.** { *; }
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Coroutines
-dontwarn kotlinx.coroutines.**

# Compose runtime generated classes
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Firebase
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# Moshi/serialization adapters generated via KSP
-keep class **JsonAdapter

# OkHttp logging
-dontwarn okio.**

# WorkManager
-dontwarn androidx.work.impl.**

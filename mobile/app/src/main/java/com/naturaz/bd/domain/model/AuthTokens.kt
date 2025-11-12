package com.naturaz.bd.domain.model

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val expiresAtEpochSeconds: Long
) {
    fun isExpired(currentEpochSeconds: Long = System.currentTimeMillis() / 1000): Boolean =
        expiresAtEpochSeconds <= currentEpochSeconds + TOKEN_EXPIRY_BUFFER_SECONDS

    companion object {
        private const val TOKEN_EXPIRY_BUFFER_SECONDS = 60L
    }
}

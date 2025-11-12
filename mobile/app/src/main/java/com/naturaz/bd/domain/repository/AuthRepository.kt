package com.naturaz.bd.domain.repository

import com.naturaz.bd.core.common.NaturazResult
import com.naturaz.bd.domain.model.AuthTokens
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun loginWithEmail(email: String, password: String): NaturazResult<AuthTokens>
    suspend fun loginWithPhone(phone: String, otp: String): NaturazResult<AuthTokens>
    suspend fun loginWithGoogle(idToken: String): NaturazResult<AuthTokens>
    suspend fun loginWithFacebook(accessToken: String): NaturazResult<AuthTokens>
    suspend fun refreshTokens(): NaturazResult<AuthTokens>
    suspend fun registerUser(
        fullName: String,
        email: String,
        phone: String,
        password: String
    ): NaturazResult<Unit>
    suspend fun logout(): NaturazResult<Unit>
    fun observeAccessToken(): Flow<String?>
}

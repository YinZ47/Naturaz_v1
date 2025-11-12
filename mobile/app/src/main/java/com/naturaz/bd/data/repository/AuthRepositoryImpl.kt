package com.naturaz.bd.data.repository

import com.naturaz.bd.core.common.NaturazResult
import com.naturaz.bd.core.common.DispatcherProvider
import com.naturaz.bd.core.network.safeApiCall
import com.naturaz.bd.core.storage.TokenManager
import com.naturaz.bd.data.remote.api.UserApi
import com.naturaz.bd.data.remote.dto.EmailLoginRequestDto
import com.naturaz.bd.data.remote.dto.PhoneLoginRequestDto
import com.naturaz.bd.data.remote.dto.RegisterRequestDto
import com.naturaz.bd.data.remote.dto.SocialLoginRequestDto
import com.naturaz.bd.domain.model.AuthTokens
import com.naturaz.bd.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val tokenManager: TokenManager,
    private val dispatchers: DispatcherProvider
) : AuthRepository {
    override suspend fun loginWithEmail(email: String, password: String): NaturazResult<AuthTokens> =
        withContext(dispatchers.io) {
            when (val result = safeApiCall { userApi.loginWithEmail(EmailLoginRequestDto(email, password)) }) {
                is NaturazResult.Success -> {
                    val response = result.data
                    tokenManager.saveTokens(response.accessToken, response.refreshToken, response.expiresIn)
                    NaturazResult.Success(
                        AuthTokens(
                            accessToken = response.accessToken,
                            refreshToken = response.refreshToken,
                            expiresAtEpochSeconds = System.currentTimeMillis() / 1000 + response.expiresIn
                        )
                    )
                }
                is NaturazResult.Error -> result
                NaturazResult.Loading -> NaturazResult.Loading
            }
        }

    override suspend fun loginWithPhone(phone: String, otp: String): NaturazResult<AuthTokens> =
        withContext(dispatchers.io) {
            when (val result = safeApiCall { userApi.loginWithPhone(PhoneLoginRequestDto(phone, otp)) }) {
                is NaturazResult.Success -> {
                    val response = result.data
                    tokenManager.saveTokens(response.accessToken, response.refreshToken, response.expiresIn)
                    NaturazResult.Success(
                        AuthTokens(
                            accessToken = response.accessToken,
                            refreshToken = response.refreshToken,
                            expiresAtEpochSeconds = System.currentTimeMillis() / 1000 + response.expiresIn
                        )
                    )
                }
                is NaturazResult.Error -> result
                NaturazResult.Loading -> NaturazResult.Loading
            }
        }

    override suspend fun loginWithGoogle(idToken: String): NaturazResult<AuthTokens> =
        withContext(dispatchers.io) {
            when (val result = safeApiCall { userApi.loginWithSocial(SocialLoginRequestDto("google", idToken)) }) {
                is NaturazResult.Success -> {
                    val response = result.data
                    tokenManager.saveTokens(response.accessToken, response.refreshToken, response.expiresIn)
                    NaturazResult.Success(
                        AuthTokens(
                            accessToken = response.accessToken,
                            refreshToken = response.refreshToken,
                            expiresAtEpochSeconds = System.currentTimeMillis() / 1000 + response.expiresIn
                        )
                    )
                }
                is NaturazResult.Error -> result
                NaturazResult.Loading -> NaturazResult.Loading
            }
        }

    override suspend fun loginWithFacebook(accessToken: String): NaturazResult<AuthTokens> =
        withContext(dispatchers.io) {
            when (val result = safeApiCall { userApi.loginWithSocial(SocialLoginRequestDto("facebook", accessToken)) }) {
                is NaturazResult.Success -> {
                    val response = result.data
                    tokenManager.saveTokens(response.accessToken, response.refreshToken, response.expiresIn)
                    NaturazResult.Success(
                        AuthTokens(
                            accessToken = response.accessToken,
                            refreshToken = response.refreshToken,
                            expiresAtEpochSeconds = System.currentTimeMillis() / 1000 + response.expiresIn
                        )
                    )
                }
                is NaturazResult.Error -> result
                NaturazResult.Loading -> NaturazResult.Loading
            }
        }

    override suspend fun refreshTokens(): NaturazResult<AuthTokens> = withContext(dispatchers.io) {
        when (val result = safeApiCall { userApi.refreshTokens() }) {
            is NaturazResult.Success -> {
                val response = result.data
                tokenManager.saveTokens(response.accessToken, response.refreshToken, response.expiresIn)
                NaturazResult.Success(
                    AuthTokens(
                        accessToken = response.accessToken,
                        refreshToken = response.refreshToken,
                        expiresAtEpochSeconds = System.currentTimeMillis() / 1000 + response.expiresIn
                    )
                )
            }
            is NaturazResult.Error -> result
            NaturazResult.Loading -> NaturazResult.Loading
        }
    }

    override suspend fun registerUser(fullName: String, email: String, phone: String, password: String): NaturazResult<Unit> =
        withContext(dispatchers.io) {
            safeApiCall {
                userApi.registerUser(
                    RegisterRequestDto(
                        fullName = fullName,
                        email = email,
                        phone = phone,
                        password = password
                    )
                )
            }.mapToUnit()
        }

    override suspend fun logout(): NaturazResult<Unit> = withContext(dispatchers.io) {
        when (val result = safeApiCall { userApi.logout() }) {
            is NaturazResult.Success -> {
                tokenManager.clearTokens()
                NaturazResult.Success(Unit)
            }
            is NaturazResult.Error -> result
            NaturazResult.Loading -> NaturazResult.Loading
        }
    }

    override fun observeAccessToken(): Flow<String?> = tokenManager.accessTokenFlow

    private suspend fun NaturazResult<Unit>.mapToUnit(): NaturazResult<Unit> = this
}

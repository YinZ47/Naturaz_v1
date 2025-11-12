package com.naturaz.bd.core.network

import com.naturaz.bd.core.storage.TokenManager
import com.naturaz.bd.data.remote.api.UserApi
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NaturazAuthenticator @Inject constructor(
    private val userApi: UserApi,
    private val tokenManager: TokenManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            Timber.w("Received 401. Attempting token refresh")
            val refreshToken = tokenManager.refreshToken ?: return null
            return runBlocking {
                val result = kotlin.runCatching { userApi.refreshTokens() }
                result.getOrNull()?.let {
                    tokenManager.saveTokens(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                        expiresIn = it.expiresIn
                    )
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${it.accessToken}")
                        .build()
                }
            }
        }
        return null
    }
}

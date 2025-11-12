package com.naturaz.bd.core.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.naturaz.bd.core.common.ApplicationScope
import com.naturaz.bd.core.security.TokenCryptoManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val cryptoManager: TokenCryptoManager,
    @ApplicationScope private val applicationScope: CoroutineScope
) {
    private val accessTokenKey = stringPreferencesKey("access_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")
    private val expiresAtKey = longPreferencesKey("expires_at")

    private val tokenStateFlow = dataStore.data
        .map { preferences ->
            TokenState(
                accessToken = cryptoManager.decrypt(preferences[accessTokenKey]),
                refreshToken = cryptoManager.decrypt(preferences[refreshTokenKey]),
                expiresAt = preferences[expiresAtKey] ?: 0L
            )
        }
        .stateIn(
            scope = applicationScope,
            started = SharingStarted.Eagerly,
            initialValue = TokenState()
        )

    val accessTokenFlow: Flow<String?> = tokenStateFlow.map { it.accessToken }
    val refreshTokenFlow: Flow<String?> = tokenStateFlow.map { it.refreshToken }

    val accessToken: String?
        get() = tokenStateFlow.value.accessToken

    val refreshToken: String?
        get() = tokenStateFlow.value.refreshToken

    suspend fun saveTokens(accessToken: String, refreshToken: String, expiresIn: Long) {
        val encryptedAccess = cryptoManager.encrypt(accessToken)
        val encryptedRefresh = cryptoManager.encrypt(refreshToken)
        val expiresAt = System.currentTimeMillis() / 1000 + expiresIn
        dataStore.edit { prefs ->
            prefs[accessTokenKey] = encryptedAccess
            prefs[refreshTokenKey] = encryptedRefresh
            prefs[expiresAtKey] = expiresAt
        }
    }

    suspend fun clearTokens() {
        dataStore.edit { prefs ->
            prefs.remove(accessTokenKey)
            prefs.remove(refreshTokenKey)
            prefs.remove(expiresAtKey)
        }
    }

    suspend fun isTokenExpired(): Boolean {
        val state = tokenStateFlow.first()
        return state.expiresAt <= (System.currentTimeMillis() / 1000) + TOKEN_EXPIRY_BUFFER_SECONDS
    }

    private data class TokenState(
        val accessToken: String? = null,
        val refreshToken: String? = null,
        val expiresAt: Long = 0L
    )

    private companion object {
        private const val TOKEN_EXPIRY_BUFFER_SECONDS = 60L
    }
}

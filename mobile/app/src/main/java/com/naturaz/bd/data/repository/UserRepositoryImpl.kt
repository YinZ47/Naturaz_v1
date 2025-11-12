package com.naturaz.bd.data.repository

import com.naturaz.bd.core.common.NaturazResult
import com.naturaz.bd.core.common.DispatcherProvider
import com.naturaz.bd.core.network.safeApiCall
import com.naturaz.bd.data.mapper.toDomain
import com.naturaz.bd.data.mapper.toDto
import com.naturaz.bd.data.remote.api.UserApi
import com.naturaz.bd.domain.model.User
import com.naturaz.bd.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val dispatchers: DispatcherProvider,
    @com.naturaz.bd.core.common.ApplicationScope private val appScope: CoroutineScope
) : UserRepository {

    private val userState = MutableStateFlow<NaturazResult<User>>(NaturazResult.Loading)

    init {
        appScope.launch(dispatchers.io) {
            refreshProfile()
        }
    }

    override fun observeUser(): Flow<NaturazResult<User>> = userState.asStateFlow()

    override suspend fun refreshProfile(): NaturazResult<User> = withContext(dispatchers.io) {
        when (val result = safeApiCall { userApi.getProfile() }) {
            is NaturazResult.Success -> {
                val user = result.data.toDomain()
                userState.value = NaturazResult.Success(user)
                userState.value
            }
            is NaturazResult.Error -> {
                userState.value = result
                result
            }
            NaturazResult.Loading -> NaturazResult.Loading
        }
    }

    override suspend fun updateProfile(user: User): NaturazResult<User> = withContext(dispatchers.io) {
        when (val result = safeApiCall { userApi.updateProfile(user.id, user.toDto()) }) {
            is NaturazResult.Success -> {
                val domain = result.data.toDomain()
                userState.value = NaturazResult.Success(domain)
                userState.value
            }
            is NaturazResult.Error -> {
                userState.value = result
                result
            }
            NaturazResult.Loading -> NaturazResult.Loading
        }
    }

    override suspend fun toggleBiometric(enabled: Boolean): NaturazResult<Unit> = withContext(dispatchers.io) {
        val current = (userState.value as? NaturazResult.Success)?.data
        if (current != null) {
            val updated = current.copy(biometricEnabled = enabled)
            userState.value = NaturazResult.Success(updated)
        }
        NaturazResult.Success(Unit)
    }
}

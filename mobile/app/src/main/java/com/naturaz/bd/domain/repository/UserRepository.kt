package com.naturaz.bd.domain.repository

import com.naturaz.bd.core.common.NaturazResult
import com.naturaz.bd.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUser(): Flow<NaturazResult<User>>
    suspend fun refreshProfile(): NaturazResult<User>
    suspend fun updateProfile(user: User): NaturazResult<User>
    suspend fun toggleBiometric(enabled: Boolean): NaturazResult<Unit>
}

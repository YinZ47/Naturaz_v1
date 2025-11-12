package com.naturaz.bd.data.remote.api

import com.naturaz.bd.data.remote.dto.AuthResponseDto
import com.naturaz.bd.data.remote.dto.EmailLoginRequestDto
import com.naturaz.bd.data.remote.dto.PhoneLoginRequestDto
import com.naturaz.bd.data.remote.dto.RegisterRequestDto
import com.naturaz.bd.data.remote.dto.SocialLoginRequestDto
import com.naturaz.bd.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @POST("api/v1/users/auth/login/email")
    suspend fun loginWithEmail(@Body body: EmailLoginRequestDto): AuthResponseDto

    @POST("api/v1/users/auth/login/phone")
    suspend fun loginWithPhone(@Body body: PhoneLoginRequestDto): AuthResponseDto

    @POST("api/v1/users/auth/login/social")
    suspend fun loginWithSocial(@Body body: SocialLoginRequestDto): AuthResponseDto

    @POST("api/v1/users/auth/register")
    suspend fun registerUser(@Body body: RegisterRequestDto)

    @POST("api/v1/users/auth/logout")
    suspend fun logout()

    @POST("api/v1/users/auth/refresh")
    suspend fun refreshTokens(): AuthResponseDto

    @GET("api/v1/users/me")
    suspend fun getProfile(): UserDto

    @PUT("api/v1/users/{id}")
    suspend fun updateProfile(@Path("id") id: String, @Body body: UserDto): UserDto
}

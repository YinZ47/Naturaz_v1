package com.naturaz.bd.core.common

sealed interface NaturazResult<out T> {
    data class Success<T>(val data: T) : NaturazResult<T>
    data class Error(
        val message: String,
        val cause: Throwable? = null,
        val type: ErrorType = ErrorType.UNKNOWN,
        val code: Int? = null
    ) : NaturazResult<Nothing>
    data object Loading : NaturazResult<Nothing>
}

enum class ErrorType {
    NETWORK,
    UNAUTHORIZED,
    VALIDATION,
    SERVER,
    NOT_FOUND,
    RATE_LIMITED,
    UNKNOWN
}

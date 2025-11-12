package com.naturaz.bd.core.network

import com.naturaz.bd.core.common.ErrorType
import com.naturaz.bd.core.common.NaturazResult
import java.io.IOException
import kotlinx.serialization.decodeFromString
import okhttp3.ResponseBody
import retrofit2.HttpException

suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): NaturazResult<T> =
    try {
        NaturazResult.Success(block())
    } catch (http: HttpException) {
        NaturazResult.Error(
            message = parseHttpError(http)?.message ?: "Server error",
            cause = http,
            type = when (http.code()) {
                400, 422 -> ErrorType.VALIDATION
                401 -> ErrorType.UNAUTHORIZED
                404 -> ErrorType.NOT_FOUND
                429 -> ErrorType.RATE_LIMITED
                in 500..599 -> ErrorType.SERVER
                else -> ErrorType.UNKNOWN
            },
            code = http.code()
        )
    } catch (io: IOException) {
        NaturazResult.Error(
            message = "Check your internet connection and try again.",
            cause = io,
            type = ErrorType.NETWORK
        )
    } catch (ex: Exception) {
        NaturazResult.Error(
            message = ex.message ?: "Unexpected error",
            cause = ex,
            type = ErrorType.UNKNOWN
        )
    }

@PublishedApi
internal fun parseHttpError(exception: HttpException): ApiErrorResponse? {
    val errorBody = exception.response()?.errorBody() ?: return null
    return errorBody.parseErrorBody()
}

@PublishedApi
internal fun ResponseBody.parseErrorBody(): ApiErrorResponse? = runCatching {
    NaturazJson.decodeFromString<ApiErrorResponse>(string())
}.getOrNull()

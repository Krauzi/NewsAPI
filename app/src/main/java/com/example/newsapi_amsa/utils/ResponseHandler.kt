package com.example.newsapi_amsa.utils

import retrofit2.HttpException
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resource.error(getErrorMessage(ErrorCodes.SocketTimeOut.code), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            400 -> "Bad Request. The request was unacceptable, often due to a missing or misconfigured parameter."
            401 -> "Unauthorised. Your API key was missing from the request, or wasn't correct."
            429 -> "Too Many Requests. You made too many requests within a window of time and have been rate limited. Back off for a while."
            404 -> "Not found"
            500 -> "Something went wrong on our side."
            else -> "Something else."
        }
    }
}
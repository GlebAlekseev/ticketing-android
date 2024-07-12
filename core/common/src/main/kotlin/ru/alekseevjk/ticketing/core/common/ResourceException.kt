package ru.alekseevjk.ticketing.core.common

sealed class ResourceException: Exception() {
    data class HTTPException(val throwable: Throwable): ResourceException()
    data class IOException(val throwable: Throwable): ResourceException()
    data class SocketTimeoutException(val throwable: Throwable): ResourceException()
    data class NoInternetConnectionException(val throwable: Throwable): ResourceException()
    data class UnknownException(val throwable: Throwable): ResourceException()
}
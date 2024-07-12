package ru.alekseevjk.ticketing.core.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val resourceException: ResourceException) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
    data object Idle : Resource<Nothing>()
}

fun <T, U> Resource<T>.map(transform: (T) -> U): Resource<U> {
    return when (val state = this) {
        is Resource.Success -> Resource.Success(transform.invoke(state.data))
        is Resource.Failure -> state
        Resource.Idle -> Resource.Idle
        Resource.Loading -> Resource.Loading
    }
}
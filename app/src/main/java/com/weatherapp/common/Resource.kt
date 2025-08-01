package com.weatherapp.common

sealed class Resource<T>(val data: T? = null, val error: NetworkError? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(error: NetworkError, data: T? = null) : Resource<T>(data, error)
    class Loading<T>() : Resource<T>()
}
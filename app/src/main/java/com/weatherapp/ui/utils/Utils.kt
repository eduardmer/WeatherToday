package com.weatherapp.ui.utils

import android.content.Context
import com.weatherapp.common.NetworkError
import com.weatherapp.R

fun Double?.toCelsius() =
    if (this != null)
        "%.1f°".format(this - 273.15)
    else ""

fun Context.getErrorMessage(error: NetworkError): String {
    return error.description.ifEmpty {
        when (error) {
            NetworkError.HTTP_BAD_REQUEST -> getString(R.string.error_server_400)
            NetworkError.HTTP_UNAUTHORIZED -> getString(R.string.error_server_401)
            NetworkError.HTTP_FORBIDDEN -> getString(R.string.error_server_403)
            NetworkError.HTTP_INTERNAL_SERVER_ERROR -> getString(R.string.error_server_500)
            NetworkError.NO_INTERNET_CONNECTION -> getString(R.string.no_internet_connection)
            NetworkError.HTTP_NOT_FOUND -> getString(R.string.error_city_not_found)
            NetworkError.HTTP_NOT_IMPLEMENTED, NetworkError.HTTP_BAD_GATEWAY, NetworkError.TIMEOUT, NetworkError.GENERIC_ERROR ->
                    getString(R.string.generic_error_occurred)
        }
    }
}
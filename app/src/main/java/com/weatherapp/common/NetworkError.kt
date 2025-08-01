package com.weatherapp.common

enum class NetworkError {

    NO_INTERNET_CONNECTION,
    HTTP_BAD_REQUEST, //400
    HTTP_UNAUTHORIZED, //401
    HTTP_FORBIDDEN, //403
    HTTP_NOT_FOUND, //404
    HTTP_INTERNAL_SERVER_ERROR, //500
    HTTP_NOT_IMPLEMENTED, //501
    HTTP_BAD_GATEWAY, //502
    TIMEOUT, // 1200 // Timeout
    GENERIC_ERROR; //Generic

    private var code: Int? = null
    var description: String = ""

    private fun setDescriptionFromServer(description: String, code: Int?) {
        this.description = description
        this.code = code
    }

    companion object {
        fun errorForHttpCode(code: Int?, description: String? = ""): NetworkError {
            val error = when (code) {
                -1 -> NO_INTERNET_CONNECTION
                400 -> HTTP_BAD_REQUEST
                401 -> HTTP_UNAUTHORIZED
                403 -> HTTP_FORBIDDEN
                404 -> HTTP_NOT_FOUND
                500 -> HTTP_INTERNAL_SERVER_ERROR
                501 -> HTTP_NOT_IMPLEMENTED
                502 -> HTTP_BAD_GATEWAY
                1200 -> TIMEOUT
                else -> GENERIC_ERROR
            }
            error.setDescriptionFromServer(description.orEmpty(), code)
            return error
        }
    }
}
package com.example.clima.data.source.remote.exceptions

open class MyNetworkException(
    message: String? = null,
    cause: Throwable? = null,
    url: String? = null,
    httpStatus: Int? = null
) :
    Exception(message ?: cause?.message, cause)
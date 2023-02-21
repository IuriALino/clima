package com.example.clima.data.source.remote.exceptions

class NotFoundException(
    message: String? = null,
    cause: Throwable? = null,
    url: String? = null,
    httpStatus: Int? = null
) :
    MyNetworkException(message, cause, url, httpStatus)
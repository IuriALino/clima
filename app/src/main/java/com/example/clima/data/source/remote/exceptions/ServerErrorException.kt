package com.example.clima.data.source.remote.exceptions

class ServerErrorException(
    message: String? = "Erro no servidor",
    cause: Throwable? = null,
    url: String? = null,
    httpStatus: Int? = null
) :
    MyNetworkException(message, cause, url, httpStatus)
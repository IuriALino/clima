package com.example.clima.data.source

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

suspend fun <T> Response<T>?.defaultFailure(failure: (t: JsonObject) -> Unit) = withContext(
    Dispatchers.IO
) {
    val response = this@defaultFailure
    try {
        val json = response?.errorBody()?.string()
        val jsonObject = JsonParser().parse(json).asJsonObject
        jsonObject.addProperty("httpStatus", response?.code())
        failure(jsonObject)
    } catch (e: Exception) {
        val jsonObject = JsonObject()
        jsonObject.addProperty(
            "message",
            "Ocorreu um erro no recebimento dos dados.\nPor favor tente novamente."
        )
        failure(jsonObject)
    }
}

suspend fun Throwable?.defaultError(failure: (JsonObject) -> Unit) = withContext(Dispatchers.IO) {
    val throwable = this@defaultError
    throwable?.let {
        try {
            val jsonObject = JsonObject()
            jsonObject.addProperty("message", it.getFormatedMessage())
            jsonObject.addProperty("localizedMessage", it.localizedMessage)
            jsonObject.addProperty("throwable", it.toString())
            failure(jsonObject)
        } catch (e: Exception) {
            val jsonObject = JsonObject()
            jsonObject.addProperty(
                "message",
                "Ocorreu um erro no recebimento dos dados.\nPor favor tente novamente."
            )
            failure(jsonObject)
        }
    }
}

fun Throwable.getFormatedMessage(): String {
    return when (this) {
        is ConnectException -> "Erro ao estabelecer conexão com o servidor"
        is UnknownHostException -> "Verifique sua conexão com a internet"
        else -> this.message ?: "Ocorreu um erro inesperado"
    }
}

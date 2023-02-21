package com.example.clima.data.source.remote

import android.content.Context
import com.example.clima.R
import com.example.clima.data.source.getFormatedMessage
import com.example.clima.data.source.remote.exceptions.MyNetworkException
import com.example.clima.data.source.remote.exceptions.NotFoundException
import com.example.clima.data.source.remote.exceptions.ServerErrorException
import com.google.gson.JsonParser
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

@KoinApiExtension
object RequestManager : KoinComponent {
    private val context by inject<Context>()


    suspend fun <T> requestFromApi(
        request: (suspend () -> Response<T>)
    ): T? {
        var response: Response<T>? = null
        try {
            response = request()
            if (response.isSuccessful) {
                return response.body()
            } else {
                val message = try {
                    JsonParser.parseString(
                        response.errorBody()?.string()
                    ).asJsonObject?.get("message")?.asString ?: response.message()
                } catch (e: Exception) {
                    ""
                }

                val requestUrl = response.raw().request.url.toString()


                throw when (response.code()) {
                    in 500..599 -> ServerErrorException(
                        message = "${response.code()} - " + context.getString(
                            R.string.error_internal_server
                        ),
                        url = requestUrl,
                        httpStatus = response.code()
                    )
                    401 -> MyNetworkException(
                        message = context.getString(R.string.error_login_invalid),
                        url = requestUrl,
                        httpStatus = response.code()
                    )
                    404 -> {
                        NotFoundException(
                            message = context.getString(R.string.error_login_invalid),
                            url = requestUrl,
                            httpStatus = response.code()
                        )
                    }
                    else -> MyNetworkException(
                        message = message, url = requestUrl,
                        httpStatus = response.code()
                    )
                }
            }
        } catch (e: Exception) {
//            fireBaseAnalytics.logEventRequestException(e, response?.raw()?.request?.url?.toString())
//            crashlytics.recordException(e)
            throw when (e) {
                is NotFoundException,
                is ServerErrorException -> e
                else -> MyNetworkException(e.getFormatedMessage(), e.cause)
            }
        }
    }
}
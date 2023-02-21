package com.example.clima.data.source.remote.interceptor

import com.example.clima.data.source.remote.HttpCustom
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@KoinApiExtension
class InterceptorAPI : Interceptor, KoinComponent {
    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            return chain.request().newBuilder().apply {
                addHeader(HttpCustom.Headers.CONTENT_TYPE, "application/json")
            }.run {
                chain.proceed(build())
            }
        }
    }
}
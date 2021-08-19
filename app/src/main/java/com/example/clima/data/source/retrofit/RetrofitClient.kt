package com.example.clima.data.source.retrofit

import com.example.clima.BuildConfig
import com.example.clima.data.source.retrofit.service.WeatherService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@KoinApiExtension
object RetrofitClient : KoinComponent {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org")
        .client(httpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun httpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(45, TimeUnit.SECONDS)
            writeTimeout(45, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
            addInterceptor(loggingInterceptor)
            addInterceptor(accessTokenInterceptor())
        }.build()
    }

    private fun accessTokenInterceptor() = Interceptor { chain ->
        val request = chain.request()
        request.newBuilder()

        chain.proceed(
            chain.request().newBuilder()
                .addHeader("Accept-Language", "pt-BR")
                .addHeader("Accept", "application/json")
                .addHeader("Connection", "close")
                .build()
        )
    }
    fun weatherService(): WeatherService = retrofit.create(WeatherService::class.java)
}
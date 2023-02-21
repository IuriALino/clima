package com.example.clima.data.di

import com.example.clima.BuildConfig
import com.example.clima.data.source.remote.interceptor.InterceptorAPI
import com.example.clima.data.source.remote.service.WeatherAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@OptIn(KoinApiExtension::class)
val networkModule = module {
    factory {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(get<HttpLoggingInterceptor>())
    }
    factory {
        InterceptorAPI()
    }
    single { createWebService<WeatherAPI>(okHttpClientBuilder = get()) }
    single {
        createWebService<WeatherAPI>(
            okHttpClientBuilder = get(),
            interceptors = arrayOf(get<InterceptorAPI>())
        )
    }
}


inline fun <reified T> createWebService(
    okHttpClientBuilder: OkHttpClient.Builder,
    vararg interceptors: Interceptor,
    url: String = BuildConfig.BASE_URL,
): T {

    interceptors.forEach {
        okHttpClientBuilder.addInterceptor(it)
    }

    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .client(okHttpClientBuilder.build())
        .build()
        .create(T::class.java)
}
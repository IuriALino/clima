package com.example.clima.data.source.remote.service

import com.example.clima.BuildConfig
import com.example.clima.data.source.remote.dto.WeatherDTO
import com.example.clima.data.source.remote.Route
import com.example.clima.data.source.remote.dto.ForeCastDTO
import org.koin.core.component.KoinApiExtension
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

@KoinApiExtension
interface WeatherAPI {
    @GET(Route.Weather.FEATCH_ONE)
    suspend fun featchWeather(
        @Query("q") location: String,
        @Query("appid") appid: String = BuildConfig.PROVIDER_AUTH
    ): Response<WeatherDTO>

    @GET(Route.Weather.FEATCH_FIVE)
    suspend fun featchForecast(
        @Query("q") location: String,
        @Query("appid") appid: String = BuildConfig.PROVIDER_AUTH
    ): Response<ForeCastDTO>

}
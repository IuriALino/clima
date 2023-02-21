package com.example.clima.data.source.retrofit.service


import com.example.clima.BuildConfig
import com.example.clima.data.source.retrofit.Route
import com.example.clima.data.source.remote.dto.ForeCastDTO
import com.example.clima.data.source.remote.dto.WeatherDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

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
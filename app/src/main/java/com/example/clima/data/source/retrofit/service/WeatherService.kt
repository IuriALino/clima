package com.example.clima.data.source.retrofit.service


import com.example.clima.BuildConfig
import com.example.clima.data.source.retrofit.Route
import com.example.clima.data.source.retrofit.response.ForeCastResponse
import com.example.clima.data.source.retrofit.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET(Route.Weather.FETCH_ONE)
    suspend fun fetchWeather(
        @Query("q") location: String,
        @Query("appid") appid: String = BuildConfig.PROVIDER_AUTH
    ): Response<WeatherResponse>

    @GET(Route.Weather.FETCH_FIVE)
    suspend fun fetchForecast(
        @Query("q") location: String,
        @Query("appid") appid: String = BuildConfig.PROVIDER_AUTH
    ): Response<ForeCastResponse>

}
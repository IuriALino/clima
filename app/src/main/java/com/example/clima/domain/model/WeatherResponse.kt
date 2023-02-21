package com.example.clima.domain.model

data class WeatherResponseDomain(
    val base: String,
    val clouds: CloudsDomain,
    val cod: Int,
    val coord: CoordDomain,
    val dt: Int,
    val id: Int,
    val main: MainDomain,
    val name: String,
    val sys: SysDomain,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherDomain>,
    val wind: WindDomain,
    val dtTxt: String,
)

data class CloudsDomain(
    val all: Int
)

data class CoordDomain(
    val lat: Double,
    val lon: Double
)

data class MainDomain(
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double
)

data class SysDomain(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)

data class WeatherDomain(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class WindDomain(
    val deg: Int,
    val speed: Double
)
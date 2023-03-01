package com.example.clima.domain.model

data class WeatherDomain(
    val base: String? = "",
    val clouds: CloudsDomain? = CloudsDomain(),
    val cod: Int? = 0,
    val coord: CoordDomain? = CoordDomain(),
    val dt: Int? = 0,
    val id: Int? = 0,
    val main: MainDomain? = MainDomain(),
    val name: String? = "",
    val sys: SysDomain? = SysDomain(),
    val timezone: Int? = 0,
    val visibility: Int? = 0,
    val weather: List<WeatherListDomain>? = listOf(),
    val wind: WindDomain? = WindDomain(),
    val dtTxt: String? = "",
)

data class CloudsDomain(
    val all: Int? = 0
)

data class CoordDomain(
    val lat: Double? = 0.0,
    val lon: Double? = 0.0
)

data class MainDomain(
    val feelsLike: Double? = 0.0,
    val humidity: Int? = 0,
    val pressure: Int? = 0,
    var temp: String? = "",
    val tempMax: Double? = 0.0,
    val tempMin: Double? = 0.0
)

data class SysDomain(
    val country: String? = "",
    val id: Int? = 0,
    val sunrise: Int? = 0,
    val sunset: Int? = 0,
    val type: Int? = 0
)

data class WeatherListDomain(
    val description: String? = "",
    val icon: String? = "",
    val id: Int? = 0,
    val main: String
)

data class WindDomain(
    val deg: Int? = 0,
    val speed: Double? = 0.0
)
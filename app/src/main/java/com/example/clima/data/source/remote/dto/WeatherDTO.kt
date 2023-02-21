package com.example.clima.data.source.remote.dto
import com.example.clima.data.source.kelvinToCelsius
import com.example.clima.domain.model.*
import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("base")
    val base: String,
    @SerializedName("clouds")
    val clouds: CloudsDTO,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("coord")
    val coord: CoordDTO,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: MainDTO,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    val sys: SysDTO,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<WeatherListDTO>,
    @SerializedName("wind")
    val wind: WindDTO,
    @SerializedName("dt_txt")
    val dtTxt: String,
)

data class CloudsDTO(
    @SerializedName("all")
    val all: Int
)

data class CoordDTO(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)

data class MainDTO(
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)

data class SysDTO(
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("type")
    val type: Int
)

data class WeatherListDTO(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)

data class WindDTO(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("speed")
    val speed: Double
)

fun CloudsDTO.toDomain() = CloudsDomain(
    all = all
)

fun CoordDTO.toDomain() = CoordDomain(
    lat = lat,
    lon = lon
)

fun MainDTO.toDomain() = MainDomain(
    feelsLike = feelsLike,
    humidity = humidity,
    pressure = pressure,
    temp = temp.kelvinToCelsius(),
    tempMax = tempMax,
    tempMin = tempMin
)

fun SysDTO.toDomain() = SysDomain(
    country = country,
    id = id,
    sunrise = sunrise,
    sunset = sunset,
    type = type
)

fun WeatherListDTO.toDomain() = WeatherListDomain(
    description = description,
    icon = icon,
    id = id,
    main = main
)

fun WindDTO.toDomain() = WindDomain(
    deg = deg,
    speed = speed
)
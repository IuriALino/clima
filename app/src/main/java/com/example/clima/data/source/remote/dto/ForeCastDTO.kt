package com.example.clima.data.source.remote.dto
import com.google.gson.annotations.SerializedName

data class ForeCastDTO(
    @SerializedName("city")
    val city: CityDTO,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<WeatherDTO>,
    @SerializedName("message")
    val message: Int,
)

data class CityDTO(
    @SerializedName("coord")
    val coord: CoordDTO,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("population")
    val population: Int,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("timezone")
    val timezone: Int
)

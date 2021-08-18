package com.example.clima.data.model

import com.example.clima.data.source.kelvinToCelsius
import com.example.clima.data.source.retrofit.response.WeatherResponse

data class WeatherModel(
    var temperature : String,
    var city : String
){
    companion object {
        fun fromResponse(weatherResponse: WeatherResponse) = WeatherModel(weatherResponse.main.temp.kelvinToCelsius(), weatherResponse.name)
    }
}


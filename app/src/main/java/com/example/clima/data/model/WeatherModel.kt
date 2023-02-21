package com.example.clima.data.model

import com.example.clima.data.source.kelvinToCelsius
import com.example.clima.data.source.remote.dto.WeatherDTO

data class WeatherModel(
    var temperature : String,
    var city : String
){
    companion object {
        fun fromResponse(weatherResponse: WeatherDTO) = WeatherModel(weatherResponse.main.temp.kelvinToCelsius(), weatherResponse.name)
    }
}


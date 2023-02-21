package com.example.clima.data.source.remote.mapper.weather

import com.example.clima.data.source.remote.dto.WeatherDTO
import com.example.clima.data.source.remote.dto.toDomain
import com.example.clima.data.source.remote.mapper.RemoteMapper
import com.example.clima.data.source.room.entity.WeatherEntity
import com.example.clima.domain.model.WeatherDomain

object WeatherRemoteMapper : RemoteMapper<WeatherDTO, WeatherEntity, WeatherDomain> {
    override fun toEntity(dto: WeatherDTO): WeatherEntity? {
        return null
    }

    override fun toDomain(dto: WeatherDTO) = with(dto) {
        WeatherDomain(
            base = base,
            clouds = clouds.toDomain(),
            cod = cod,
            coord = coord.toDomain(),
            dt = dt,
            id = id,
            main = main.toDomain(),
            sys = sys.toDomain(),
            name = name,
            timezone = timezone,
            visibility = visibility,
            weather = weather.map { it.toDomain() },
            wind = wind.toDomain(),
            dtTxt = dtTxt
        )
    }
}


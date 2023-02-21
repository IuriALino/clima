package com.example.clima.domain.di

import com.example.clima.domain.usecase.weather.FeatchWeatherUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { FeatchWeatherUseCase(weatherRepository = get()) }
}
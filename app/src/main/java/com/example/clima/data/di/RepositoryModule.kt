package com.example.clima.data.di

import com.example.clima.data.repository.WeatherRepositoryimpl
import com.example.clima.domain.repository.WeatherRepository
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val repositoryModule = module {
    factory<WeatherRepository> {
        WeatherRepositoryimpl(
            weatherAPI = get(),
            authStorage = get()
        )
    }
}
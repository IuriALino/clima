package com.example.clima.domain.usecase.forecast

import com.example.clima.domain.model.ForeCastDomain
import com.example.clima.domain.repository.WeatherRepository
import com.example.clima.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow

class GetForeCastUseCase(
    private val weatherRepository: WeatherRepository
): UseCase<String, Flow<List<ForeCastDomain>?>> {
    override suspend fun execute(param: String) = weatherRepository.getForecast(param)
}
package com.example.clima.data.source.room.storage


import com.example.clima.data.model.ForeCastModel
import com.example.clima.data.source.removeSpecialCharacters
import com.example.clima.data.source.room.dao.AuthDAO
import com.example.clima.data.source.room.entity.WeatherEntity
import com.example.clima.domain.model.ForeCastDomain
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class AuthStorage(
    private val authDAO: AuthDAO
    ) {

    suspend fun saveDataForeCast(foreCastModel: ForeCastDomain) = withContext(IO + NonCancellable) {
        authDAO.insert(
            WeatherEntity(
                date = foreCastModel.date,
                temperature = foreCastModel.temperature,
                city = foreCastModel.city.uppercase().removeSpecialCharacters()
            )
        )
    }

    suspend fun getForecast(city : String) = withContext(IO) {
        city.removeSpecialCharacters()?.let { authDAO.getForecast(it.uppercase()) }
    }

    suspend fun deleteCity(city : String) = withContext(IO) {
        city.removeSpecialCharacters()?.let { authDAO.deleteCity(it.uppercase()) }
    }
}
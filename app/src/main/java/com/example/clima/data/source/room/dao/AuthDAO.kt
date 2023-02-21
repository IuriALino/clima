package com.example.clima.data.source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.clima.data.model.ForeCastModel
import com.example.clima.data.source.room.entity.WeatherEntity
import org.koin.core.component.KoinApiExtension

@Dao
interface AuthDAO {
    @KoinApiExtension
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(authEntity: WeatherEntity): Long

    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME} WHERE ${WeatherEntity.COLUMN_CITY} = :city")
    suspend fun getForecast(city:String) : List<ForeCastModel>

    @Query("DELETE FROM ${WeatherEntity.TABLE_NAME} WHERE ${WeatherEntity.COLUMN_CITY} = :city")
    suspend fun deleteCity(city: String)
}
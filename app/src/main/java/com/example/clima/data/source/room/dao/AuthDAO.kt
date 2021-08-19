package com.example.clima.data.source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.clima.data.model.ForeCastModel
import com.example.clima.data.source.room.entity.AuthEntity
import org.koin.core.component.KoinApiExtension


@Dao
interface AuthDAO {
    @KoinApiExtension
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(authEntity: AuthEntity): Long

    @Query("SELECT * FROM ${AuthEntity.TABLE_NAME} WHERE ${AuthEntity.COLUMN_CITY} = :city")
    suspend fun getForecast(city:String) : List<ForeCastModel>

    @Query("DELETE FROM ${AuthEntity.TABLE_NAME} WHERE ${AuthEntity.COLUMN_CITY} = :city")
    suspend fun deleteCity(city: String)

}
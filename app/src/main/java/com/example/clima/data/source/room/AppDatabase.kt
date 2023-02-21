package com.example.clima.data.source.room

import androidx.room.*
import androidx.room.Database
import com.example.clima.data.source.room.dao.AuthDAO
import com.example.clima.data.source.room.entity.WeatherEntity
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
@Database(
    entities = [
        WeatherEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authDAO(): AuthDAO

}
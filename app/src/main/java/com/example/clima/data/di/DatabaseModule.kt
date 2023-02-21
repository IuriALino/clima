package com.example.clima.data.di

import androidx.room.Room
import com.example.clima.data.source.room.AppDatabase
import com.example.clima.data.source.room.Database
import com.example.clima.data.source.room.storage.AuthStorage
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val databasemodule = module {
    single {
        Room
            .databaseBuilder(get(), AppDatabase::class.java, Database.NAME)
            .allowMainThreadQueries()
            .build()
    }
    single { get<AppDatabase>().authDAO() }
    single { AuthStorage(authDAO = get()) }
}
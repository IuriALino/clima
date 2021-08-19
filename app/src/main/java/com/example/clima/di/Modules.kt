package com.example.clima.di

import android.content.Context
import androidx.room.Room
import com.example.clima.data.repo.WeatherRepository
import com.example.clima.data.source.retrofit.RetrofitClient
import com.example.clima.data.source.retrofit.client.WeatherAPIClient
import com.example.clima.data.source.room.AppDatabase
import com.example.clima.data.source.room.storage.AuthStorage
import com.example.clima.ui.WeatherViewModel
import com.example.clima.view.ProgressLoader
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@KoinApiExtension
object Modules {

    val app = module {
        factory { WeatherAPIClient(get()) }
        factory { WeatherRepository(weatherAPIClient = get(), context= get(), authStorage = get())}
        viewModel { WeatherViewModel(get()) }
        single { RetrofitClient }
        factory { (context: Context) -> ProgressLoader(context) }
        single {
            Room
                .databaseBuilder(get(), AppDatabase::class.java, "DB")
                .allowMainThreadQueries()
                .build()
        }
        single { get<AppDatabase>().authDAO() }
        single { AuthStorage(authDAO = get()) }

    }
}
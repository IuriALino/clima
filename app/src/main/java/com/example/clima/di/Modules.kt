package com.example.clima.di

import android.content.Context
import android.view.ViewGroup
import com.example.clima.data.repo.WeatherRepository
import com.example.clima.data.source.retrofit.RetrofitClient
import com.example.clima.data.source.retrofit.client.WeatherAPIClient
import com.example.clima.ui.WeatherViewModel
import okhttp3.internal.platform.android.AndroidSocketAdapter.Companion.factory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@KoinApiExtension
object Modules {

    val app = module {
        factory { WeatherAPIClient(retrofit = get()) }
        factory { WeatherRepository(get(), get())}
        viewModel { WeatherViewModel(get()) }
        single { RetrofitClient }

    }
}
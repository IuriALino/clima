package com.example.clima.ui.di

import android.content.Context
import com.example.clima.data.di.databasemodule
import com.example.clima.data.di.networkModule
import com.example.clima.data.di.repositoryModule
import com.example.clima.domain.di.useCaseModule
import com.example.clima.ui.WeatherViewModel
import com.example.clima.view.ProgressLoader
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@KoinApiExtension
object Modules {

    val app = module {
        viewModel { WeatherViewModel(get()) }
        factory { (context: Context) -> ProgressLoader(context) }
    }

    val modules = listOf(
        app,
        databasemodule,
        repositoryModule,
        networkModule,
        useCaseModule

    )
}
package com.example.clima.ui.di

import android.content.Context
import com.example.clima.data.di.databasemodule
import com.example.clima.data.di.networkModule
import com.example.clima.data.di.repositoryModule
import com.example.clima.domain.di.useCaseModule
import com.example.clima.ui.features.WeatherViewModel
import com.example.clima.view.ProgressLoader
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@KoinApiExtension
object Modules {

    private val ui = module {
        factory { (context: Context) -> ProgressLoader(context) }
    }

    private val viewModel = module {
        viewModel {
            WeatherViewModel(
                weatherUseCase = get(),
                foreCastUseCase = get()
            )
        }
    }

    val modules = listOf(
        ui,
        databasemodule,
        repositoryModule,
        networkModule,
        useCaseModule,
        viewModel
    )
}
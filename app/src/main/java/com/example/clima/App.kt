package com.example.clima

import android.app.Application
import com.example.clima.presentation.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin

import org.koin.core.logger.Level

@KoinApiExtension
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(Modules.modules)
        }
    }
}
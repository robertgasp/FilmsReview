package com.example.filmsreview

import android.app.Application
import org.koin.android.ext.koin.androidContext
import com.example.filmsreview.di.appModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}
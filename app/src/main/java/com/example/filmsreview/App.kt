package com.example.filmsreview

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import com.example.filmsreview.di.appModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    companion object{
        lateinit var appContext: Context
    }
}
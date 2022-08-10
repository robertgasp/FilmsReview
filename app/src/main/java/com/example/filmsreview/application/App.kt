package com.example.filmsreview.application

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import com.example.filmsreview.di.appModule
import com.example.filmsreview.di.filmsModule
import com.example.filmsreview.room.AppDataBase
import com.example.filmsreview.room.dao.CacheFilmDao
import com.example.filmsreview.room.dao.FavoriteFilmDao
import com.example.filmsreview.room.dao.HistoryDao
import org.koin.core.context.startKoin
import java.lang.IllegalStateException

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
        startKoin {
            androidContext(this@App)
            modules(appModule, filmsModule)
        }
    }

    companion object {
        private var appInstance: App? = null
        private var db: AppDataBase? = null
        private const val DB_NAME = "AppDataBase.db"

        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                synchronized(AppDataBase::class.java) {
                    if (appInstance == null) {
                        throw IllegalStateException("Application ids null meanwhile creating database")
                    }
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        AppDataBase::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return db!!.historyDao()
        }

        fun getCacheDao(): CacheFilmDao {
            if (db == null) {
                synchronized(AppDataBase::class.java) {
                    if (appInstance == null) {
                        throw IllegalStateException("Application ids null meanwhile creating database")
                    }
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        AppDataBase::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return db!!.cacheDao()
        }

        fun getFavoriteFilmDao(): FavoriteFilmDao {
            if (db == null) {
                synchronized(AppDataBase::class.java) {
                    if (appInstance == null) {
                        throw IllegalStateException("Application ids null meanwhile creating database")
                    }
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        AppDataBase::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()
                        .build()
                }

            }
            return db!!.favoriteFilmDao()
        }
    }
}
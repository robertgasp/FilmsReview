package com.example.filmsreview.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmsreview.room.entity.CacheFilmEntity
import com.example.filmsreview.room.entity.HistoryEntity
import com.example.filmsreview.room.dao.CacheFilmDao
import com.example.filmsreview.room.dao.FavoriteFilmDao
import com.example.filmsreview.room.dao.HistoryDao
import com.example.filmsreview.room.entity.FavoriteFilmEntity

@Database(
    entities = [HistoryEntity::class, CacheFilmEntity::class, FavoriteFilmEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun cacheDao(): CacheFilmDao
    abstract fun favoriteFilmDao(): FavoriteFilmDao
}
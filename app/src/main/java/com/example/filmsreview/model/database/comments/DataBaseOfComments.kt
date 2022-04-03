package com.example.filmsreview.model.database.comments

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.filmsreview.App

@androidx.room.Database(
    entities = [CommentsEntity::class],
    version = 1,
    exportSchema = false
)

abstract class DataBaseOfComments : RoomDatabase() {
    abstract fun commentsDao(): CommentsDao

    companion object {
        private const val DB_NAME = "add_databaseOfComments.db"
        val db: DataBaseOfComments by lazy {
            Room.databaseBuilder(
                App.appContext,
                DataBaseOfComments::class.java,
                DB_NAME
            ).build()
        }
    }
}
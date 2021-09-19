package com.example.filmsreview.model.database.history
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.filmsreview.App

@androidx.room.Database(
    entities = [HistoryEntity::class],
    version = 1,
    exportSchema = false
)

abstract class DataBase:RoomDatabase(){
    abstract fun historyDao(): HistoryDao

    companion object{
        private const val DB_NAME = "add_database.db"
        val db: DataBase by lazy {
            Room.databaseBuilder(
                App.appContext,
                DataBase ::class.java,
                DB_NAME
            ).build()
        }
    }
}
package com.example.filmsreview.room.dao

import androidx.room.*
import com.example.filmsreview.room.entity.HistoryEntity

@Dao
interface HistoryDao {

    @Query ("SELECT * FROM HistoryEntity")
    fun all():List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE id=:id")
    fun getById(id:Long):HistoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(historyEntity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)
}
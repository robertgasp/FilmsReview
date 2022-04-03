package com.example.filmsreview.model.database.history

import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    fun all():List<HistoryEntity>

    @Query("SELECT * FROM historyentity WHERE id = :idFromQuery")
    fun getDataById (idFromQuery:Long):List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (entity: HistoryEntity)

    @Update
    fun update (entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Query ("DELETE FROM HistoryEntity WHERE id =:idFromQueryToDelete")
    fun deleteById(idFromQueryToDelete:Long)


}
package com.example.filmsreview.model.database.comments

import androidx.room.*

@Dao
interface CommentsDao {

    @Query("SELECT * FROM CommentsEntity")
    fun all(): List<CommentsEntity>

    @Query("SELECT * FROM commentsentity WHERE id = :idFromQuery")
    fun getDataById(idFromQuery: Long): CommentsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: CommentsEntity)

    @Update
    fun update(entity: CommentsEntity)

    @Delete
    fun delete(entity: CommentsEntity)

    @Query("DELETE FROM CommentsEntity WHERE id =:idFromQueryToDelete")
    fun deleteById(idFromQueryToDelete: Long)
}
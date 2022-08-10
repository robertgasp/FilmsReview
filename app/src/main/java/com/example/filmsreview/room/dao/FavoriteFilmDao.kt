package com.example.filmsreview.room.dao

import androidx.room.*
import com.example.filmsreview.room.entity.FavoriteFilmEntity

@Dao
interface FavoriteFilmDao {

    @Query("SELECT * FROM FavoriteFilmEntity")
    fun getAll(): List<FavoriteFilmEntity>

    @Query("SELECT * FROM FavoriteFilmEntity WHERE id=:id")
    fun getFavoriteFilmById(id: Long): FavoriteFilmEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteFilmEntity: FavoriteFilmEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(films:List<FavoriteFilmEntity>)

    @Delete
    fun delete(favoriteFilmEntity: FavoriteFilmEntity)
}
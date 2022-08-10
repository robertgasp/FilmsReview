package com.example.filmsreview.room.dao

import androidx.room.*
import com.example.filmsreview.room.entity.CacheFilmEntity

@Dao
interface CacheFilmDao {

    @Query("SELECT * FROM CacheFilmEntity")
    fun all(): List<CacheFilmEntity>

    @Query("SELECT * FROM CacheFilmEntity WHERE film_id=:id")
    fun getCacheFilmByFilmId(id: Long): CacheFilmEntity

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert (cacheFilmEntity: CacheFilmEntity)

    @Update
    fun update(cacheFilmEntity: CacheFilmEntity)

    @Delete
    fun delete(cacheFilmEntity: CacheFilmEntity)
}
package com.example.filmsreview.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["film_id"], unique = true)])
data class CacheFilmEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "film_id")
    var filmId: Long,
    @ColumnInfo(name = "poster_path")
    var posterPath: String,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "release_date")
    var releaseDate:String,
    @ColumnInfo(name = "media_type")
    var mediaType : String,
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double,
    @ColumnInfo(name = "overview")
    var overview:String,
    @ColumnInfo(name = "adult")
    var adult: Boolean = false
)
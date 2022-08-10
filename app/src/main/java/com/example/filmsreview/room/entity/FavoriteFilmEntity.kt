package com.example.filmsreview.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteFilmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "comments")
    val userComments: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)

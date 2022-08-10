package com.example.filmsreview.room.entity

import androidx.room.*

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "date_of_watching")
    var dateOfWatching: String,
)

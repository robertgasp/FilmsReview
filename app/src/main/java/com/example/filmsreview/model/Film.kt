package com.example.filmsreview.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    private val cover: Int,
    private val title: String,
    private val year: Int,
    private val genre: String
) : Parcelable {

    fun getCover(): Int {
        return cover
    }

    fun getTitle(): String {
        return title
    }

    fun getYear(): Int {
        return year
    }

    fun getGenre(): String {
        return genre
    }
}
package com.example.filmsreview.model.repository

import com.google.gson.annotations.SerializedName

class FilmsList {

    @SerializedName("results")
    private var films: List<Film>? = null

    fun getFilmList(): List<Film> {
        return films ?: listOf()
    }
}
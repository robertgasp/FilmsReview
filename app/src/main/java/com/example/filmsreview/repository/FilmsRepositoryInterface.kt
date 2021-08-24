package com.example.filmsreview.model

import android.view.View
import com.example.filmsreview.repository.FilmsList

interface FilmsRepositoryInterface {
    //fun getFilmList(): List<FilmsList>
    fun getFilm(): List<Film>
    fun getFilm(
        api_key: String,
        id: Int
    ): Film
}
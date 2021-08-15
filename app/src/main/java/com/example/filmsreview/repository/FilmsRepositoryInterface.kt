package com.example.filmsreview.model

import android.view.View
import com.example.filmsreview.repository.FilmsList

interface FilmsRepositoryInterface {
    fun getListFilms(): List<FilmsList>
    fun getFilm():FilmsList
}
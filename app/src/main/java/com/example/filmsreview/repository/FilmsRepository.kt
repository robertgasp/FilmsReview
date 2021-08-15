package com.example.filmsreview.repository

import android.view.View
import com.example.filmsreview.R
import com.example.filmsreview.model.Film
import com.example.filmsreview.model.FilmsRepositoryInterface

class FilmsRepository : FilmsRepositoryInterface {

    override fun getListFilms(): List<FilmsList> = getFilmsList()

    override fun getFilm() = FilmsList()

}
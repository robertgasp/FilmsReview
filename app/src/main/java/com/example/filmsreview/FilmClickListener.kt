package com.example.filmsreview

import com.example.filmsreview.model.Film
import com.example.filmsreview.repository.FilmsList

interface FilmClickListener {
    fun filmClicked(film: Film)
}
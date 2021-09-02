package com.example.filmsreview

import com.example.filmsreview.model.Film
import com.example.filmsreview.repository.FilmsList
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj

interface FilmClickListener {
    fun filmClicked(film: FactDataObj)
}
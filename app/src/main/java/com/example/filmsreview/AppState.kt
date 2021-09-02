package com.example.filmsreview

import com.example.filmsreview.model.Film
import com.example.filmsreview.repository.FilmsList
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.example.filmsreview.repository.rest.rest_entities.FilmsListDataObj

sealed class AppState {
    data class Success(val filmsData: List<FactDataObj>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
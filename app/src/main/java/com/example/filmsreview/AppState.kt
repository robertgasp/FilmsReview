package com.example.filmsreview

import com.example.filmsreview.model.Film
import com.example.filmsreview.repository.FilmsList

sealed class AppState {
    data class Success(val filmsData: List<Film>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
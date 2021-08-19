package com.example.filmsreview

import com.example.filmsreview.repository.FilmsList

sealed class AppState {
    data class Success(val filmsData: List<FilmsList>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
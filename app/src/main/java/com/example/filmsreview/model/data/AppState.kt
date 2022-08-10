package com.example.filmsreview.model.data

import com.example.filmsreview.model.repository.Film
import com.example.filmsreview.room.entity.HistoryEntity

sealed class AppState {

    data class Success(val filmsData: List<Film>) : AppState()
    data class Error(val throwable: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()

    data class HistorySuccess(val historyData: List<HistoryEntity>) : AppState()
    data class FavoriteSuccess(val favoriteData: List<Film>) : AppState()
}

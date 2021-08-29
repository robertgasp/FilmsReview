package com.example.filmsreview.repository

import com.example.filmsreview.R
import android.os.Parcelable
import com.example.filmsreview.model.Film
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmsList(val film: Film = getDefaultFilm()) : Parcelable


fun getDefaultFilm(): Film =
    Film(500, R.drawable.unknown_cover, "Окулус", 1999, "Триллер", 8.5, "Фильм-страшилка")
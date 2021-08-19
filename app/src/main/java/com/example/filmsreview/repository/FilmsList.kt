package com.example.filmsreview.repository

import com.example.filmsreview.R
import com.example.filmsreview.model.FilmsRepositoryInterface
import android.os.Parcelable
import com.example.filmsreview.model.Film
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmsList(val film: Film = getDefaultFilm()) : Parcelable

fun getFilmsList(): List<FilmsList> = listOf(
    FilmsList(Film(R.drawable.unknown_cover, "Окулус", 2014, "Триллер")),
    FilmsList(Film(R.drawable.unknown_cover, "Окулус", 2014, "Триллер")),
    FilmsList(Film(R.drawable.unknown_cover, "Окулус", 2014, "Триллер")),
    FilmsList(Film(R.drawable.unknown_cover, "Окулус", 2014, "Триллер")),
    FilmsList(Film(R.drawable.unknown_cover, "Окулус", 2014, "Триллер")),
    FilmsList(Film(R.drawable.unknown_cover, "Окулус", 2014, "Триллер")),
    FilmsList(Film(R.drawable.unknown_cover, "Окулус", 2014, "Триллер")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма")),
    FilmsList(Film(R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма"))
)

fun getDefaultFilm(): Film = Film(R.drawable.unknown_cover, "Окулус", 2014, "Триллер")

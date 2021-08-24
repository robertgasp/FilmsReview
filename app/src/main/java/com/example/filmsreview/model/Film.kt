package com.example.filmsreview.model

import android.os.Parcelable
import com.example.filmsreview.R
import com.example.filmsreview.repository.FilmsList
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Film(
    private val id: Int? = 0,
    private val logo_path: Int? = 0,
    private val name: String? = null,
    private val release_date: Int? = null,
    private val genres: String? = null,
    private val vote_average: Double?,
    private val overview: String? = null
) : Parcelable {

    fun getID(): Int? = id

    fun getLogoPath(): Int? = logo_path

    fun getName(): String? = name

    fun getReleaseDate(): Int? = release_date

    fun getGenres(): String? = genres

    fun getOverView(): String? = overview


    companion object {
        fun getFilmsList(): List<Film> = listOf(
            Film(500, R.drawable.unknown_cover, "Окулус", 1999, "Триллер", 8.5, "Фильм-страшилка"),
            Film(500, R.drawable.unknown_cover, "Окулус", 1999, "Триллер", 8.5, "Фильм-страшилка"),
            Film(500, R.drawable.unknown_cover, "Окулус", 1999, "Триллер", 8.5, "Фильм-страшилка"),
            Film(500, R.drawable.unknown_cover, "Окулус", 1999, "Триллер", 8.5, "Фильм-страшилка"),
            Film(500, R.drawable.unknown_cover, "Окулус", 1999, "Триллер", 8.5, "Фильм-страшилка"),

            Film(600, R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма", 9.1, ""),
            Film(600, R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма", 9.1, ""),
            Film(600, R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма", 9.1, ""),
            Film(600, R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма", 9.1, ""),
            Film(600, R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма", 9.1, ""),
            Film(600, R.drawable.unknown_cover, "Зеленая миля", 1999, "Драма", 9.1, ""),
        )
    }

}
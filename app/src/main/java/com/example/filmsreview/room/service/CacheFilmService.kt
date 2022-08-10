package com.example.filmsreview.room.service

import com.example.filmsreview.model.dto.FilmDto

interface CacheFilmService {
    fun findById(filmId: Long): FilmDto
    fun saveFilmToCache(film: FilmDto)
}
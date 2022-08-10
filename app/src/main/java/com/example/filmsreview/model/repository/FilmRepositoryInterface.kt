package com.example.filmsreview.model.repository

interface FilmRepositoryInterface<T, U> {
    suspend fun getListOfFilmsFromInternetAsync(apiKey: String, language: String): T
    suspend fun getSingleFilmFromInternetAsync(id:Long,apiKey: String, language: String): U
}
package com.example.filmsreview.model.dataSource

interface InetDataSource<T, U> {

    suspend fun getDataAsync(apiKey: String, language: String): T
    suspend fun getFilmAsync(id: Long, apiKey: String, language: String): U
}
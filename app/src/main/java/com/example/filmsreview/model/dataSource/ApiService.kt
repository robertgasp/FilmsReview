package com.example.filmsreview.model.dataSource

import com.example.filmsreview.model.repository.Film
import com.example.filmsreview.model.repository.FilmsList
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("trending/movie/day")
    fun getListOfFilmsAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Deferred<FilmsList>

    @GET("movie/{id}")
    fun getSingleFilmAsync(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Deferred<Film>
}
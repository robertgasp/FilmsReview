package com.example.filmsreview.repository.rest.rest_entities

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsAPI {

    @GET("movie/{id}")

    fun getOneFilmFromInternet(
        @Path("id")id:Int?,
        @Query("language") lang: String
    ): Call<FactDataObj>
}
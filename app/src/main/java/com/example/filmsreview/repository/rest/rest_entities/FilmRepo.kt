package com.example.filmsreview.repository.rest.rest_entities

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object FilmRepo {
    val api:FilmsAPI by lazy{
        val adapter =Retrofit.Builder()
            .baseUrl(ApiUtils.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build()

        adapter.create(FilmsAPI::class.java)
    }

}
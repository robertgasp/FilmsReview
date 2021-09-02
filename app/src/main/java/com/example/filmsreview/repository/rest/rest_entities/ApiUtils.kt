package com.example.filmsreview.repository.rest.rest_entities

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiUtils {
    private val baseUrlMainPart = "https://api.themoviedb.org/"
    private val baseUrlVersion = "3/"
    val baseURL = "$baseUrlMainPart$baseUrlVersion"

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header(
                    "Authorization",
                    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwYmNhOGE3NzIzMDExNmI4YWM0M2NkM2I4NjM0YWNhOSIsInN1YiI6IjYxMGVjY2M5YWUzODQzMDAzMDY4NTA0ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.m4TwF9F_jh0DRDGy3qSbqH6r0MeC_TB6DBTcGrkbr5w"
                )
                .method(original.method(), original.body())
                .build()
            chain.proceed(request)
        }
        return httpClient.build()
    }


}
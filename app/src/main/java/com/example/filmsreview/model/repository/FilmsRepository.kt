package com.example.filmsreview.model.repository

import com.example.filmsreview.model.dataSource.InetDataSource

class FilmsRepository(private var dataSource: InetDataSource<FilmsList, Film>) :
    FilmRepositoryInterface<FilmsList, Film> {

    override suspend fun getListOfFilmsFromInternetAsync(
        apiKey: String,
        language: String
    ): FilmsList {
        return dataSource.getDataAsync(apiKey, language)
    }

    override suspend fun getSingleFilmFromInternetAsync(
        id: Long,
        apiKey: String,
        language: String
    ): Film {
        return dataSource.getFilmAsync(id,apiKey,language)
    }

}
package com.example.filmsreview.repository

import com.example.filmsreview.model.FilmsRepositoryInterface
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj

class FilmsRepository : FilmsRepositoryInterface {


    override fun getFilmFromInternet(): List<FactDataObj>? = FactDataObj.getFilmsListFromInternet()


    override fun getFilm(id: String?): FactDataObj {
        val dataObj = FilmsListLoader.loadFilmList(id)

        return FactDataObj(
            id = dataObj?.film!!.id,
            posterPath = dataObj?.film?.posterPath,
            title = dataObj?.film?.title,
            releaseDate = dataObj?.film?.releaseDate,
            originCountry = dataObj?.film.originCountry,
            genres = dataObj?.film?.genres,
            voteAverage = dataObj?.film?.voteAverage,
            overview = dataObj?.film?.overview
        )
    }
}
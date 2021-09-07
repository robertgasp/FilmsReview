package com.example.filmsreview.repository

import com.example.filmsreview.model.FilmsRepositoryInterface
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.example.filmsreview.repository.rest.rest_entities.FilmRepo

class FilmsRepository : FilmsRepositoryInterface {


    override fun getFilmFromInternet(): List<FactDataObj> = FactDataObj.getFilmsListFromInternet()


    override fun getFilm(id: Int?): FactDataObj {
        //val dataObj = FilmsListLoader.loadFilmList(id)
        val lang = "ru-RU"
        val dataObj = FilmRepo.api.getOneFilmFromInternet(id,lang).execute().body()

        return FactDataObj(
            id = dataObj?.id,
            posterPath = dataObj?.posterPath,
            title = dataObj?.title,
            releaseDate = dataObj?.releaseDate,
            mediaType = dataObj?.mediaType,
            voteAverage = dataObj?.voteAverage,
            overview = dataObj?.overview,
            adult = dataObj?.adult
        )
    }
}
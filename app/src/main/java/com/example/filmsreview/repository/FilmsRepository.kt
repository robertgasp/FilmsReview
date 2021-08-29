package com.example.filmsreview.repository

import com.example.filmsreview.model.Film
import com.example.filmsreview.model.FilmsRepositoryInterface

class FilmsRepository : FilmsRepositoryInterface {

    //в дальнейшем хочу использовать этот метод для получения подборок фильмов
//    override fun getFilms(): List<FilmsList> = getFilmsList()
    override fun getFilm(): List<Film> = Film.getFilmsList()

    override fun getFilm(api_key: String, id: Int): Film {
        val dataObj = FilmsListLoader.loadFilmList(api_key, id)

        return Film(
            id = dataObj?.film?.id,
            logo_path = dataObj?.film?.logo_path,
            name = dataObj?.film?.name,
            release_date = dataObj?.film?.release_date,
            origin_country=dataObj?.film?.origin_country,
            genres = dataObj?.film?.genres,
            vote_average = dataObj?.film?.vote_average,
            overview = dataObj?.film?.overview
        )
    }
}
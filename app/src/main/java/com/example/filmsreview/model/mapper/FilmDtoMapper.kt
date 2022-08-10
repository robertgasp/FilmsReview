package com.example.filmsreview.model.mapper

import com.example.filmsreview.application.App
import com.example.filmsreview.model.repository.Film
import com.example.filmsreview.model.dto.FilmDto
import com.example.filmsreview.room.entity.CacheFilmEntity
import com.example.filmsreview.room.entity.FavoriteFilmEntity
import com.example.filmsreview.room.entity.HistoryEntity

class FilmDtoMapper {

    companion object {
        fun filmEntityToFilmDao(
            filmEntity: CacheFilmEntity,
            favoriteFilmEntity: FavoriteFilmEntity
        ): FilmDto {
            return FilmDto(
            filmId = filmEntity.filmId,
            posterPath = filmEntity.posterPath,
            title = filmEntity.title,
            releaseDate = filmEntity.releaseDate,
            mediaType = filmEntity.mediaType,
            voteAverage = filmEntity.voteAverage,
            overview = filmEntity.overview,
            adult = filmEntity.adult,
            isFavorite = favoriteFilmEntity.isFavorite)
        }

        fun filmDtoToFilmEntity(filmDto: FilmDto): CacheFilmEntity {

            return CacheFilmEntity(
                filmDto.filmId,
                filmDto.posterPath,
                filmDto.title,
                filmDto.releaseDate,
                filmDto.mediaType,
                filmDto.voteAverage,
                filmDto.overview,
                filmDto.adult
            )
        }

        fun filmDtoToFavoriteFilmEntity(filmDto: FilmDto): FavoriteFilmEntity {
            return FavoriteFilmEntity(filmDto.filmId,"", true)
        }

        fun filmToFilmDto(filmObject: Film): FilmDto {
            return FilmDto(
            filmId = filmObject.id ?: -1L,
            posterPath = filmObject.posterPath ?: "",
            title = filmObject.title ?: "",
            releaseDate = filmObject.releaseDate ?: "",
            mediaType = filmObject.mediaType ?: "",
            voteAverage = filmObject.voteAverage ?: 0.0,
            overview = filmObject.overview ?: "",
            adult = filmObject.adult ?: false,
            isFavorite = false)
        }

        fun filmEntityToHistoryEntity(filmObject: Film, dateRequest: String): HistoryEntity {
            return HistoryEntity(filmObject.id, dateRequest)
        }

        fun historyEntityToFilmObject(historyEntity: HistoryEntity): Film {

            val film = App.getCacheDao().getCacheFilmByFilmId(historyEntity.id)
            return Film(
                film.filmId,
                film.posterPath,
                film.title,
                film.releaseDate,
                film.mediaType,
                film.voteAverage,
                film.overview,
                film.adult
            )
        }
    }
}
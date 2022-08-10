package com.example.filmsreview.room.service

import androidx.room.Transaction
import com.example.filmsreview.model.dto.FilmDto
import com.example.filmsreview.model.mapper.FilmDtoMapper
import com.example.filmsreview.room.dao.CacheFilmDao
import com.example.filmsreview.room.dao.FavoriteFilmDao

class CacheFilmServiceImpl(private val cacheFilmDao: CacheFilmDao, private var favoriteFilmDao: FavoriteFilmDao):CacheFilmService {

    @Transaction
    override fun findById(filmId: Long): FilmDto {
        val filmEntity = cacheFilmDao.getCacheFilmByFilmId(filmId)
        val favoriteFilmEntity = favoriteFilmDao.getFavoriteFilmById(filmId)
        return FilmDtoMapper.filmEntityToFilmDao(filmEntity,favoriteFilmEntity)
    }

    @Transaction
    override fun saveFilmToCache(film: FilmDto) {
        val filmEntity = FilmDtoMapper.filmDtoToFilmEntity(film)
        cacheFilmDao.insert(filmEntity)
    }
}
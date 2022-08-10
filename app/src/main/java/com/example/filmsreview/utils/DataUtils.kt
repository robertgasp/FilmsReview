package com.example.filmsreview.utils

import com.example.filmsreview.model.fireBaseDB.FavouriteFilmFirebase
import com.example.filmsreview.room.entity.FavoriteFilmEntity

fun favouriteFilmFirebaseToFavouriteFilmEntity(list: MutableList<FavouriteFilmFirebase>): List<FavoriteFilmEntity> {
    return list.map { item ->
        FavoriteFilmEntity(
            item.id,
            "",
            item.isFavorite,
        )
    }
}
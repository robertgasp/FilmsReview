package com.example.filmsreview.model

import android.view.View
import com.example.filmsreview.repository.FilmsList
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.example.filmsreview.repository.rest.rest_entities.FactDataObjForDB
import com.example.filmsreview.repository.rest.rest_entities.FilmsListDataObj

interface FilmsRepositoryInterface {
    fun getFilmFromInternet(): List<FactDataObj>?
    fun getFilm(id: String?): FactDataObj
    fun getAllHistory(): List<FactDataObjForDB>
    fun getComment(id: String?): FactDataObjForDB
    fun saveEntity(factDataObjForDB: FactDataObjForDB)
}
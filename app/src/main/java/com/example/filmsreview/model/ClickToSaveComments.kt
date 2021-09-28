package com.example.filmsreview.model

import com.example.filmsreview.repository.rest.rest_entities.FactDataObjForDB

interface ClickToSaveComments {
    fun saveComments(factDataObjForDB: FactDataObjForDB)
    //fun updateDB(factDataObjForDB: FactDataObjForDB)
}
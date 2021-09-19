package com.example.filmsreview.repository.rest.rest_entities

import android.os.Parcelable
import android.text.Editable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.*

@Parcelize
data class FactDataObjForDB(
    val factDataObj: FactDataObj,
    var dateOfWatchig: String,
    var userComments: String
) : Parcelable {

}
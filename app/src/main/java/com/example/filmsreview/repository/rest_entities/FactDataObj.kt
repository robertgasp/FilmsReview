package com.example.filmsreview.repository.rest_entities

import java.util.*

data class FactDataObj(
//    val id:Int,
    val id:Int,
    val logo_path:Int,
    val name:String,
    val release_date: Int,
    val origin_country:String,
    val genres:String,
    val vote_average:Double,
    val overview:String,
    ) {
}
package com.example.filmsreview.model.repository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    var id: Long = 0,
    @SerializedName("poster_path")
    var posterPath: String = "",

    var title: String = "",

    @SerializedName("release_date")
    var releaseDate: String = "",

    @SerializedName("media_type")
    var mediaType: String = "",

    @SerializedName("vote_average")
    var voteAverage: Double? = 0.0,
    var overview: String = "",

    @SerializedName("adult")
    var adult: Boolean = false,
) : Parcelable {

    override fun toString(): String {
        return title.toString()
    }
}



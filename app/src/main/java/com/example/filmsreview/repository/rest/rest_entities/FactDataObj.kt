package com.example.filmsreview.repository.rest.rest_entities

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.filmsreview.MainActivity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val STRING_WITH_LIST_OF_FILMS = "STRING_WITH_LIST_OF_FILMS"
const val STRING_WITH_LIST_OF_FILMS2 = "STRING_WITH_LIST_OF_FILMS"


@Parcelize
data class FactDataObj(
    var id: Int? = 0,
    @SerializedName("poster_path")
    var posterPath: String? = null,

    var title: String? = null,

    @SerializedName("release_date")
    var releaseDate: String? = null,

    @SerializedName("media_type")
    var mediaType: String? = null,

    @SerializedName("vote_average")
    var voteAverage: Double? = 0.0,
    var overview: String? = null,
    @SerializedName("adult")
    var adult: Boolean? = false,
) : Parcelable {


    companion object {
        var filmsArray18free = ArrayList<FactDataObj>()
        var filmsArray = ArrayList<FactDataObj>()
        var mainActivity = MainActivity()
        var isAdult: Boolean? = null

        fun getFilmsListFromInternet(): List<FactDataObj> {

            val uri =
                URL("https://api.themoviedb.org/3/trending/movie/day?api_key=0bca8a77230116b8ac43cd3b8634aca9&language=ru-RU")

            lateinit var urlConnection: HttpURLConnection

            try {
                isAdult = mainActivity.getAdultMode()

                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 10000
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))

                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }

                val jsonObject = JSONObject(lines)
                val jsonArray = jsonObject.getJSONArray("results")
                val tempFilmsArray18free = ArrayList<FactDataObj>()
                val tempFilmsArray = ArrayList<FactDataObj>()

                for (i in 0..jsonArray.length() - 1) {
                    val oneFilm = FactDataObj(0, "0", "", "0", "0", 0.0, "0.0", false)
                    oneFilm.id = jsonArray.getJSONObject(i).getInt("id")
                    oneFilm.posterPath =
                        "https://image.tmdb.org/t/p/original" + jsonArray.getJSONObject(i)
                            .getString("poster_path")
                    oneFilm.title = jsonArray.getJSONObject(i).getString("title")
                    oneFilm.releaseDate = jsonArray.getJSONObject(i).getString("release_date")
                    oneFilm.mediaType = jsonArray.getJSONObject(i).getString("media_type")
                    oneFilm.voteAverage = jsonArray.getJSONObject(i).getDouble("vote_average")
                    oneFilm.overview = jsonArray.getJSONObject(i).getString("overview")
                    oneFilm.adult =
                        jsonArray.getJSONObject(i).getBoolean("adult")

                    tempFilmsArray.add(oneFilm)//грузим все фильмы подряд: и без ограничений 18+, и с ограничениями

                    if (oneFilm.adult == false) {   //если isAdult == true, то грузим только с false
                        tempFilmsArray18free.add(oneFilm)
                    }

                }
                filmsArray18free = tempFilmsArray18free
                filmsArray = tempFilmsArray

                if (isAdult == true) {
                    return tempFilmsArray
                } else return tempFilmsArray18free

            } catch (e: Exception) {
                e.printStackTrace()
                return listOf()
            }
        }


        private fun getLinesForOld(reader: BufferedReader): String {
            val rawData = StringBuilder(2048)
            var tempVariable: String?
            while (reader.readLine().also { tempVariable = it } != null) {
                rawData.append(tempVariable).append("\n")
            }
            reader.close()
            return rawData.toString()
        }

        @RequiresApi(Build.VERSION_CODES.N)
        private fun getLines(reader: BufferedReader): String {
            return reader.lines().collect(Collectors.joining("\n"))
        }
    }
}
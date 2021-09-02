package com.example.filmsreview.repository.rest.rest_entities

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

@Parcelize
data class FactDataObj(
    var id: Int,
    @SerializedName("poster_path")
    var posterPath: String,

    var title: String,

    @SerializedName("release_date")
    var releaseDate: String,

    @SerializedName("origin_country")
    var originCountry: String,
    var genres: String,

    @SerializedName("vote_average")
    var voteAverage: Double,
    var overview: String,
) : Parcelable {


    companion object {

        fun getFilmsListFromInternet(): List<FactDataObj>? {
            try {
                val uri =
                    URL("https://api.themoviedb.org/3/trending/movie/day?api_key=0bca8a77230116b8ac43cd3b8634aca9&language=ru-RU")

                lateinit var urlConnection: HttpURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))

                    val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        getLinesForOld(bufferedReader)
                    } else {
                        getLines(bufferedReader)
                    }

                    val listOfFilms2 = Gson().fromJson(
                        lines,
                        FactDataObj::class.java
                    )   //здесь у меня выдает строку-массив

                    val jsonObject = JSONObject(listOfFilms2.toString())
                    val jsonArray = jsonObject.getJSONArray("results")
                    val filmsArray = ArrayList<FactDataObj>()

                    for (i in 0..jsonArray.length() - 1) {
                        val oneFilm = FactDataObj(0, "0", "", "0", "0", "0", 0.0, "0")
                        oneFilm.id = jsonArray.getJSONObject(i).getInt("id")
                        oneFilm.posterPath =
                            "https://image.tmdb.org/t/p/original" + jsonArray.getJSONObject(i)
                                .getString("poster_path")
                        oneFilm.releaseDate = jsonArray.getJSONObject(i).getString("release_date")
                        oneFilm.originCountry =
                            jsonArray.getJSONObject(i).getString("origin_country")
                        oneFilm.genres = jsonArray.getJSONObject(i).getString("genres")
                        oneFilm.voteAverage = jsonArray.getJSONObject(i).getDouble("vote_average")
                        oneFilm.overview = jsonArray.getJSONObject(i).getString("overview")
                        filmsArray.add(oneFilm)
                    }
                    return filmsArray

                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    urlConnection.disconnect()
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            return null
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
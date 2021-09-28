package com.example.filmsreview.model

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.filmsreview.R
import com.example.filmsreview.repository.FilmsList
import com.example.filmsreview.repository.FilmsListLoader
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.example.filmsreview.repository.rest.rest_entities.FilmsListDataObj
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.ArrayList


@Parcelize
data class Film(
    private val id: String? = null,
    private val logo_path: Int? = 0,
    private val name: String? = null,
    private val release_date: Int? = null,
    private val genres: String? = null,
    private val vote_average: Double?,
    private val overview: String? = null
) : Parcelable {

    fun getID(): String? = id

    fun getLogoPath(): Int? = logo_path

    fun getName(): String? = name

    fun getReleaseDate(): Int? = release_date

    fun getGenres(): String? = genres

    fun getOverView(): String? = overview


    companion object {
        //        val uri = URL("https://api.themoviedb.org/3/trending/movie/day?api_key=0bca8a77230116b8ac43cd3b8634aca9")
        private val listFilms = ArrayList<FactDataObj>()


        fun getFilmsList(): List<FactDataObj>? {

            try {
                val uri =
                    URL("https://api.themoviedb.org/3/trending/movie/day?api_key=0bca8a77230116b8ac43cd3b8634aca9")


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

                    listFilms.add(Gson().fromJson(lines, FactDataObj::class.java))
                    return listFilms

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


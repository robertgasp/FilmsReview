package com.example.filmsreview.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.example.filmsreview.repository.rest.rest_entities.STRING_WITH_LIST_OF_FILMS
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class LoadFilmsFromInternetService : Service() {

    private val binder: IBinder = ServiceBinder()

    private val filmsArray: List<FactDataObj>? = null

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    val getListOfFolimFromInterner: ArrayList<FactDataObj>
        get() {
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

                val jsonObject = JSONObject(lines)
                val jsonArray = jsonObject.getJSONArray("results")
                val filmsArray = ArrayList<FactDataObj>()

                for (i in 0..jsonArray.length() - 1) {
                    val oneFilm = FactDataObj(0, "0", "", "0", "0", 0.0, "0.0", false)
                    oneFilm.id = jsonArray.getJSONObject(i).getInt("id")
                    oneFilm.posterPath =
                        "https://image.tmdb.org/t/p/original" + jsonArray.getJSONObject(i)
                            .getString("poster_path")
                    oneFilm.releaseDate = jsonArray.getJSONObject(i).getString("release_date")
                    oneFilm.mediaType = jsonArray.getJSONObject(i).getString("media_type")
                    oneFilm.voteAverage = jsonArray.getJSONObject(i).getDouble("vote_average")
                    oneFilm.overview = jsonArray.getJSONObject(i).getString("overview")
                    oneFilm.adult =
                        jsonArray.getJSONObject(i).getBoolean("adult")
                    filmsArray.add(oneFilm)
                }
                return filmsArray

            } catch (e: Exception) {
                e.printStackTrace()
                return arrayListOf()
            } finally {
                urlConnection.disconnect()
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

    internal inner class ServiceBinder: Binder(){
        val service:LoadFilmsFromInternetService
        get()=this@LoadFilmsFromInternetService
        val getListOfFolimFromInterner: ArrayList<FactDataObj>
        get() = service.getListOfFolimFromInterner
    }
}

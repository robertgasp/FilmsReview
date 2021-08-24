package com.example.filmsreview.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.filmsreview.repository.rest_entities.FilmsListDataObj
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object FilmsListLoader {

    fun loadFilmList(api_key: String, id: Int): FilmsListDataObj? {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/$id?api_key=$api_key")

            lateinit var urlConnection: HttpURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.addRequestProperty(
                    "api_key", "0bca8a77230116b8ac43cd3b8634aca9"
                )
                urlConnection.readTimeout = 10000
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, FilmsListDataObj::class.java)
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
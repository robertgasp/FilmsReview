package com.example.filmsreview.services

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.filmsreview.repository.rest_entities.FilmsListDataObj
import com.example.filmsreview.ui.*
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

const val ID = "ID"
const val LOGO_PATH = "LOGO_PATH"

const val NAME = "NAME"
const val RELEASE_DATE = "RELEASE_DATE"
const val ORIGIN_COUNTRY = "ORIGIN_COUNTRY"

const val GENRES = "GENRES"
const val VOTE_AVERAGE = "VOTE_AVERAGE"
const val OVERVIEW = "OVERVIEW"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000
private const val REQUEST_API_KEY = "api_key"


class DescriptionService(name: String = "DescriptionService") : IntentService(name) {

    private val broadcastIntent = Intent(DESCRIPTION_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val filmId = intent.getStringExtra(ID)
            if (filmId == null) {
                onEmptyData()
            } else {
                loadMyFilmList(filmId)
            }
        }
    }

    private fun loadMyFilmList(id: String): FilmsListDataObj? {
        try {
            val apiKey = "0bca8a77230116b8ac43cd3b8634aca9"
            val uri = URL("https://api.themoviedb.org/3/movie/$id?api_key=$apiKey")

            lateinit var urlConnection: HttpURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = REQUEST_GET
                urlConnection.readTimeout = REQUEST_TIMEOUT
                urlConnection.addRequestProperty(REQUEST_API_KEY, apiKey)

                val bufferedReader =
                    BufferedReader(InputStreamReader(urlConnection.inputStream))

                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, FilmsListDataObj::class.java)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
        return null
    }


    fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(2048)
        var tempVariable: String?
        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }
        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun onResponse(filmsListDataObj: FilmsListDataObj) {
        val fact = filmsListDataObj.film

        if (fact == null) {
            onEmptyResponse()
        } else {
            onSucccessResponse(fact.id,fact.logo_path, fact.name,fact.release_date,fact.origin_country, fact.genres, fact.vote_average, fact.overview)
        }
    }

    private fun onSucccessResponse(id: Int,logo_path: Int, name: String,release_date: Int,origin_country: String,genres:String,vote_average: Double, overview:String) {
        putLoaderResult(DESCRIPTION_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(ID, id.toString())
        broadcastIntent.putExtra(NAME, name)
        broadcastIntent.putExtra(GENRES, genres)
        broadcastIntent.putExtra(OVERVIEW, overview)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoaderResult(DESCRIPTION_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoaderResult(DESCRIPTION_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DESCRIPTION_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoaderResult(DESCRIPTION_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoaderResult(DESCRIPTION_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoaderResult(DESCRIPTION_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoaderResult(result: String) {
        broadcastIntent.putExtra(DESCRIPTION_LOAD_RESULT_EXTRA, result)
    }

}
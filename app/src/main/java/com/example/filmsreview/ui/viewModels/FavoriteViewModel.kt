package com.example.filmsreview.ui.viewModels

import androidx.lifecycle.MutableLiveData
import com.example.filmsreview.BuildConfig
import com.example.filmsreview.model.repository.Film
import com.example.filmsreview.model.data.AppState
import com.example.filmsreview.model.fireBaseDB.FirebaseDbManager
import com.example.filmsreview.model.repository.FilmsList
import com.example.filmsreview.model.repository.FilmRepositoryInterface
import com.example.filmsreview.room.entity.FavoriteFilmEntity
import com.example.filmsreview.utils.favouriteFilmFirebaseToFavouriteFilmEntity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val filmsRepositoryInterface: FilmRepositoryInterface<FilmsList, Film>,
    private val firebaseDbManager: FirebaseDbManager
) :
    BaseViewModel<AppState>() {

    private var favoriteLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val apiKey: String = BuildConfig.API_KEY


    fun getFavoriteLiveData() = favoriteLiveData
    fun getFavoriteList() {
        favoriteLiveData.value = AppState.Loading(null)
        cancelJob()

        val auth = Firebase.auth
        if (auth.uid != null) {
            firebaseDbManager.getFromDB(FirebaseDbManager.FireBaseCallback {
                viewModelCoroutineScope.launch(Dispatchers.IO) {
                    val result = getMovieFromServer(favouriteFilmFirebaseToFavouriteFilmEntity(it))
                    favoriteLiveData.postValue(
                        AppState.FavoriteSuccess(result)
                    )
                }
            })
        }
    }


    private suspend fun getMovieFromServer(moviesIdList: List<FavoriteFilmEntity>): List<Film> {
        val list = mutableListOf<Film>()
        for (id in moviesIdList) {
            val movie: Film = filmsRepositoryInterface.getSingleFilmFromInternetAsync(
                id.id!!,
                apiKey,
                "ru-RU"
            )
            list.add(movie)
        }
        return list
    }


    override fun handleError(throwable: Throwable) {
        favoriteLiveData.postValue(AppState.Error(throwable))
    }

    override fun onCleared() {
//        favoriteLiveData.value = AppState.FavoriteSuccess(null)
        super.onCleared()
    }

}
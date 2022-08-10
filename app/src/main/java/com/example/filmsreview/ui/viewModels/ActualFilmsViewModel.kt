package com.example.filmsreview.ui.viewModels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.example.filmsreview.model.repository.Film
import com.example.filmsreview.model.data.AppState
import com.example.filmsreview.model.repository.FilmsList
import com.example.filmsreview.model.repository.FilmRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActualFilmsViewModel(private var repositoryInterface: FilmRepositoryInterface<FilmsList, Film>) :
    BaseViewModel<AppState>(), LifecycleObserver {

    private val myLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getMyLifeData() = myLiveData

    fun getFilms(apiKey: String, language: String) {
        myLiveData.value = AppState.Loading(null)
        cancelJob()

        viewModelCoroutineScope.launch(Dispatchers.IO) {
            myLiveData.postValue(
                AppState.Success(
                    repositoryInterface.getListOfFilmsFromInternetAsync(apiKey, language)
                        .getFilmList()
                )
            )
        }
    }

    override fun handleError(throwable: Throwable) {
        myLiveData.postValue(AppState.Error(throwable))
    }

    override fun onCleared() {
        myLiveData.value = AppState.Success(listOf())
        super.onCleared()
    }
}
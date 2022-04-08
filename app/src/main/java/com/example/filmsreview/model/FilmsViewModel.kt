package com.example.filmsreview.model

import android.util.Log
import androidx.lifecycle.*
import com.example.filmsreview.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

class FilmsViewModel(private val repositoryInterface: FilmsRepositoryInterface) : ViewModel(),
    LifecycleObserver {

    private val myLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getMyLiveData() = myLiveData

    fun getFilms() {
        myLiveData.value = AppState.Loading

       viewModelScope.launch(Dispatchers.IO) {
           sleep(1000)
           myLiveData.postValue(AppState.Success(repositoryInterface.getFilmFromInternet()))
       }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onViewStart() {
        Log.i("LifecycleEvent", "onStart")
    }
}
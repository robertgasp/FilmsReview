package com.example.filmsreview.model

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsreview.AppState
import com.example.filmsreview.repository.FilmsRepository
import java.lang.Thread.sleep

class FilmsViewModel(private val myLiveData: MutableLiveData<AppState> = MutableLiveData()) :
    ViewModel() {


    private var repositoryInterface: FilmsRepositoryInterface = FilmsRepository()

    fun getMyLiveData() = myLiveData

    fun getFilms() {
        myLiveData.value = AppState.Loading
        Thread {
            sleep(1000)
            myLiveData.postValue(AppState.Success(repositoryInterface.getListFilms()))
        }.start()
    }

}
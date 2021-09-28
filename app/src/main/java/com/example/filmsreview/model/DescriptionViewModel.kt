package com.example.filmsreview.model

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsreview.AppState

class DescriptionViewModel(private val repositoryInterface: FilmsRepositoryInterface) : ViewModel(),
    LifecycleObserver {

    val liveDataToDescribe: MutableLiveData<AppState> = MutableLiveData()

    fun loadData(id: Int?) {
        liveDataToDescribe.value = AppState.Loading
        Thread {
            val data = repositoryInterface.getFilm(id)
            val dataForDB = repositoryInterface.getComment(id)
            liveDataToDescribe.postValue(AppState.SuccessDB(data, dataForDB))
        }.start()
    }
}
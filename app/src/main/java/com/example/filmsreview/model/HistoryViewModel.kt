package com.example.filmsreview.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsreview.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class HistoryViewModel (
    private val repository: FilmsRepositoryInterface):ViewModel(), CoroutineScope by MainScope() {

    private val historyLiveData:MutableLiveData<AppState> = MutableLiveData()

    fun getAllHistory(){
        historyLiveData.value = AppState.Loading
        launch(Dispatchers.IO) {
            //historyLiveData.postValue(AppState.Success(repository.getAllHistory()))
        }
    }
}
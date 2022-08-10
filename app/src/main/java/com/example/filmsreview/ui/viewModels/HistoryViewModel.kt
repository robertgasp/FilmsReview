package com.example.filmsreview.ui.viewModels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.example.filmsreview.application.App
import com.example.filmsreview.model.data.AppState
import com.example.filmsreview.room.dao.HistoryDao
import com.example.filmsreview.room.service.HistoryService
import com.example.filmsreview.room.service.HistoryServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HistoryViewModel() : BaseViewModel<AppState>(), LifecycleObserver {

    private var historyDao: HistoryDao = App.getHistoryDao()
    private val historyLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val historyService: HistoryService = HistoryServiceImpl(historyDao)

    fun getHistoryLiveData() = historyLiveData

    fun getHistoryList() {
        historyLiveData.postValue(AppState.Loading(null))
        cancelJob()
        viewModelCoroutineScope.launch(Dispatchers.IO) {
            historyLiveData.postValue(
                AppState.HistorySuccess(
                    historyService.getAllCache()
                )
            )
        }
    }

    override fun handleError(throwable: Throwable) {
        historyLiveData.postValue(AppState.Error(throwable))
    }

    override fun onCleared() {
//        historyLiveData.value = AppState.Success(listOf())
        super.onCleared()
    }
}
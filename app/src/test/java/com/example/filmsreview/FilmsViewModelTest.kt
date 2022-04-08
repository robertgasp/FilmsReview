package com.example.filmsreview

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.filmsreview.model.FilmsRepositoryInterface
import com.example.filmsreview.model.FilmsViewModel
import com.example.filmsreview.repository.FilmsRepository
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.google.gson.annotations.SerializedName
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.internal.wait
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class FilmsViewModelTest {

    @get:Rule
    var testCoroutinesRule = TestCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var filmsViewModel: FilmsViewModel

    @Mock
    private lateinit var filmsRepository: FilmsRepositoryInterface

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        filmsViewModel = FilmsViewModel(filmsRepository)
    }

    @Test
    fun coroutines_TestReturnValueNotNull() {
        testCoroutinesRule.runBlockingTest {
            val observer = Observer<AppState> {}
            val liveData = filmsViewModel.getMyLiveData()
            Mockito.`when`(filmsRepository.getFilmFromInternet()).thenReturn(
                listOf(FactDataObj(1, null, "Title For test", "2022", null, 5.0, null, false))
            )
            try {
                liveData.observeForever(observer)
                filmsViewModel.getFilms()
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_TestReturnValueIsLoading() {
        testCoroutinesRule.runBlockingTest {
            val observer = Observer<AppState> {}
            val liveData = filmsViewModel.getMyLiveData()
            Mockito.`when`(filmsRepository.getFilmFromInternet()).thenReturn(listOf())

            try {
                liveData.observeForever(observer)
                filmsViewModel.getFilms()

//                val value : AppState.Error = liveData.value as AppState.Error
//                Assert.assertEquals(value.error.message, ERROR_TEXT)
                Assert.assertEquals(liveData.value, AppState.Loading)
            } finally {
                liveData.removeObserver(observer)
            }

        }
    }

    @Test
    fun coroutines_TestReturnValueIsError() {
        testCoroutinesRule.runBlockingTest {
            val observer = Observer<AppState> {}
            val liveData = filmsViewModel.getMyLiveData()
            Mockito.`when`(filmsRepository.getFilmFromInternet()).thenReturn(listOf())

            try {
                liveData.observeForever(observer)
                filmsViewModel.getFilms()
                val value:AppState.Error = liveData.value as AppState.Error
                Assert.assertEquals(value.error.message, null)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }


    companion object {
        private const val ERROR_TEXT = "Error in loading list of Films"
    }

}
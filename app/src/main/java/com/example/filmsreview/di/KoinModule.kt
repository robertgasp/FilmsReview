package com.example.filmsreview.di

import com.example.filmsreview.model.repository.Film
import com.example.filmsreview.model.dataSource.RetrofitImpl
import com.example.filmsreview.model.fireBaseDB.FirebaseDbManager
import com.example.filmsreview.model.repository.FilmsList
import com.example.filmsreview.model.repository.FilmRepositoryInterface
import com.example.filmsreview.model.repository.FilmsRepository
import com.example.filmsreview.ui.viewModels.FavoriteViewModel
import com.example.filmsreview.ui.viewModels.ActualFilmsViewModel
import com.example.filmsreview.ui.viewModels.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single<FilmRepositoryInterface<FilmsList, Film>> { FilmsRepository(RetrofitImpl()) }
    single<FirebaseDbManager> { FirebaseDbManager() }
}

val filmsModule = module {
    viewModel { ActualFilmsViewModel(get()) }
    viewModel { FavoriteViewModel(get(), get()) }
    single { HistoryViewModel() }
}

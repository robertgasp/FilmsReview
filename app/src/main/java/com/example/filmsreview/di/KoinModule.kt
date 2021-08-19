package com.example.filmsreview.di

import com.example.filmsreview.model.FilmsRepositoryInterface
import com.example.filmsreview.model.FilmsViewModel
import com.example.filmsreview.repository.FilmsRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single<FilmsRepositoryInterface> { FilmsRepository() }

    viewModel { FilmsViewModel(get()) }
}

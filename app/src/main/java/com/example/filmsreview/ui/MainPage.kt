package com.example.filmsreview.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.filmsreview.AppState
import com.example.filmsreview.FilmClickListener
import com.example.filmsreview.R
import com.example.filmsreview.databinding.FragmentMainPageBinding
import com.example.filmsreview.model.Film
import com.example.filmsreview.model.FilmsViewModel
import com.example.filmsreview.repository.FilmsList
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.bind


class MainPage : Fragment() {

    private lateinit var viewModel: FilmsViewModel

    private var bottomNavigation: BottomNavigationView? = null

    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!

    private var adapter: FilmsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel =
                ViewModelProvider(requireActivity()).get(FilmsViewModel::class.java)
            viewModel.getMyLiveData().observe(viewLifecycleOwner, { renderData(it) })
            viewModel.getFilms()
        }
    }

    private fun renderData(appState: AppState?) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                loadingLayout.visibility = View.GONE
                Snackbar.make(binding.bottomMenu, "Загрузка прошла успешно", Snackbar.LENGTH_LONG)
                    .show()
                adapter = FilmsAdapter(object : FilmClickListener {
                    override fun filmClicked(film: FilmsList) {
                        val manager = activity?.supportFragmentManager
                        manager.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(DescriptionPage.BUNDLE_EXTRA, film)
                            }
                            manager?.beginTransaction()
                                ?.replace(R.id.container, DescriptionPage.newInstance(bundle))
                                ?.addToBackStack("")
                                ?.commitAllowingStateLoss()
                        }
                    }
                }).apply {
                    setFilm(appState.filmsData)
                }
                filmsList.adapter = adapter
            }
            is AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
                Snackbar
                    .make(
                        binding.bottomMenu,
                        "Error",
                        Snackbar.LENGTH_INDEFINITE
                    ) //насколько понимаю, тут не важно, к какому элементу View обращаемся
                    .setAction("Ошибка") { viewModel.getFilms() }
                    .show()
            }
            null -> TODO()
        }
    }

    companion object {
        fun newInstance(): MainPage = MainPage()
    }
}
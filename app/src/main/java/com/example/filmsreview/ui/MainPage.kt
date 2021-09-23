package com.example.filmsreview.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsreview.AppState
import com.example.filmsreview.Exeptions.MyString.Companion.activity
import com.example.filmsreview.FilmClickListener
import com.example.filmsreview.MainActivity
import com.example.filmsreview.R
import com.example.filmsreview.databinding.FragmentMainPageBinding
import com.example.filmsreview.model.ClickToSaveComments
import com.example.filmsreview.model.FilmsRepositoryInterface
import com.example.filmsreview.model.FilmsViewModel
import com.example.filmsreview.repository.FilmsList
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.example.filmsreview.services.LoadFilmsFromInternetService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainPage : Fragment() {

    private val viewModel: FilmsViewModel by viewModel()

    private var bottomNavigation: BottomNavigationView? = null
    private var filmClickListenerFromMainPage: FilmClickListener? = null

    private lateinit var repository: FilmsRepositoryInterface

    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!
    private var recyclerView: RecyclerView? = null
    private var adapter: FilmsAdapter? = null
    private var isAdult: Boolean? = false
    private val IS_ADULTS_MODE = "IS_ADULTS_MODE"
    private val BottomMenuClickInterface:BottomMenuClickInterface? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FilmClickListener) {
            filmClickListenerFromMainPage = context
        }
    }


    override fun onDetach() {
        filmClickListenerFromMainPage = null
        super.onDetach()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        recyclerView = binding.filmsList
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isAdult = initDataSet()

        initRecyclerView(recyclerView)

        viewModel.getMyLiveData().observe(requireActivity(), {
            renderData(it)
            Log.d("Error", FactDataObj.getFilmsListFromInternet().toString())

            if (isAdult == true) {
                adapter?.setFilm(FactDataObj.filmsArray)
            } else adapter?.setFilm(FactDataObj.filmsArray18free)
        })
        viewModel.getFilms()


        val bottomNavigationItemView = binding.bottomNavigationMenu
        bottomNavigationItemView.setOnItemSelectedListener {
            var selectedfragment: Fragment?=null
            when (it.itemId) {
                R.id.films_collection -> {
                    selectedfragment = MainPage()
                    BottomMenuClickInterface?.selectBottomNavigationTab(selectedfragment)
                    return@setOnItemSelectedListener true
                }
                R.id.history -> {
                    Toast.makeText(requireContext(), "переключение на вкладку history", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true
                }
                R.id.favorites -> {
                    Toast.makeText(requireContext(), "переключение на вкладку favorites", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true
                }
                R.id.cinemas -> {
                    selectedfragment = CinemasAround()
                    BottomMenuClickInterface?.selectBottomNavigationTab(selectedfragment)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }


    private fun initRecyclerView(recyclerView: RecyclerView?) {
        val lm = GridLayoutManager(context, 3)
        recyclerView?.layoutManager = lm
        adapter = FilmsAdapter(this)
        recyclerView?.adapter = adapter
        filmClickListenerFromMainPage?.let { adapter?.setOnFilmClickListener(it) }
    }


    private fun renderData(appState: AppState?) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                loadingLayout.visibility = View.GONE
                /*Snackbar.make(
                    binding.bottomMenu,
                    getString(R.string.Loading_success),
                    Snackbar.LENGTH_LONG
                )
                    .show()*/
            }
            is AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
                Snackbar
                    .make(
                        binding.bottomNavigationMenu,
                        getString(R.string.Error),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.Error_rus)) { viewModel.getFilms() }
                    .show()
            }
            null -> TODO()
        }
    }


    private fun initDataSet(): Boolean? {
        activity.let { it2 ->
            it2?.getPreferences(Context.MODE_PRIVATE)?.let { preferences ->
                isAdult = preferences.getBoolean(IS_ADULTS_MODE, false)
            }
        }
        return isAdult
    }


    companion object {
        fun newInstance() = MainPage()
    }
}
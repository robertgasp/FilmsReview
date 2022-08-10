package com.example.filmsreview.ui.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsreview.BuildConfig
import com.example.filmsreview.R
import com.example.filmsreview.databinding.FragmentActualBinding
import com.example.filmsreview.model.repository.Film
import com.example.filmsreview.model.data.AppState
import com.example.filmsreview.ui.recyclerViewAdapters.ActualFilmsAdapter
import com.example.filmsreview.ui.viewModels.ActualFilmsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActualFragment : Fragment() {

    private var _binding: FragmentActualBinding? = null
    private val binding get() = _binding!!

    private val filmsViewModel: ActualFilmsViewModel by viewModel()
    private var recyclerView: RecyclerView? = null
    private var adapter: ActualFilmsAdapter? = null
    private val apiKey: String = BuildConfig.API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActualBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerViewActualFragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        filmsViewModel.getMyLifeData().observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }
        filmsViewModel.getFilms(apiKey, "ru-RU")
    }

    private fun initRecyclerView() {
        val lm = GridLayoutManager(context, 3)
        recyclerView?.layoutManager = lm
        adapter = ActualFilmsAdapter()
        recyclerView?.adapter = adapter
        adapter?.filmClickListener =
            ActualFilmsAdapter.FilmClickListener { movie -> filmClicked(movie) }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                loadingLayout.visibility = View.GONE
                adapter?.setFilm(appState.filmsData)
            }
            is AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
            }
            else -> {}
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_action_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val goToHistoryFragment =
            ActualFragmentDirections.actionActualFragmentToHistoryPageFragment()
        when (item.itemId) {
            R.id.cinemas -> findCinema()
            R.id.history -> view?.findNavController()?.navigate(goToHistoryFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun findCinema() {
        // поиск кинотеатров поблизости
        val gmmIntentUri = Uri.parse("geo:0,0?q=кинотеатр")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun filmClicked(film: Film) {
        val action = ActualFragmentDirections.actionActualFragmentToDetailsPageFragment(film)
        view?.findNavController()?.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.filmsreview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmsreview.AppState
import com.example.filmsreview.databinding.FragmentDescriptionPageBinding
import com.example.filmsreview.model.DescriptionViewModel
import com.example.filmsreview.model.Film
import com.example.filmsreview.repository.FilmsList
import org.koin.androidx.viewmodel.ext.android.viewModel


class DescriptionPage : Fragment() {

    private var _binding: FragmentDescriptionPageBinding? = null
    private val binding get() = _binding!!
    private val descriptionViewModel: DescriptionViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.getParcelable<Film>(BUNDLE_EXTRA)
        film?.let {
            with(binding) {

                descriptionViewModel.liveDataToDescribe.observe(viewLifecycleOwner, { appState ->
                    when (appState) {
                        is AppState.Error -> {
                            descriptionView.visibility = View.INVISIBLE
                            loadingLayout.visibility = View.GONE
                            errorTextView.visibility = View.VISIBLE
                        }
                        AppState.Loading -> {
                            binding.loadingLayout.visibility = View.VISIBLE
                            descriptionView.visibility = View.INVISIBLE
                        }
                        is AppState.Success -> {
                            loadingLayout.visibility = View.GONE
                            descriptionView.visibility = View.VISIBLE
                            appState.filmsData[0].getLogoPath()
                                ?.let { it1 -> cover.setImageResource(it1) }
                            title.text = appState.filmsData[0].getName().toString()
                            year.text = appState.filmsData[0].getReleaseDate().toString()
                            genre.text = appState.filmsData[0].getGenres()
                            description.text = appState.filmsData[0].getOverView()
                        }
                    }
                })
                descriptionViewModel.loadData("0bca8a77230116b8ac43cd3b8634aca9", id)
            }
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "film"
        fun newInstance(bundle: Bundle): DescriptionPage {
            val fragment = DescriptionPage()
            fragment.arguments = bundle
            return fragment
        }
    }
}
package com.example.filmsreview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmsreview.AppState
import com.example.filmsreview.databinding.FragmentDescriptionPageBinding
import com.example.filmsreview.model.DescriptionViewModel
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import org.koin.androidx.viewmodel.ext.android.viewModel


class DescriptionPage : Fragment() {

    private var _binding: FragmentDescriptionPageBinding? = null
    private val binding get() = _binding!!
    private val descriptionViewModel: DescriptionViewModel by viewModel()
    private var filmId:Int?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.getParcelable<FactDataObj>(BUNDLE_EXTRA)

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

//                            appState.filmsData!![0].posterPath
//                                ?.let { it1 -> cover.setImageResource(it1.toInt()) }
                            title.text = appState.filmsData!![0].title
                            year.text = appState.filmsData[0].releaseDate
                            mediaType.text = appState.filmsData[0].mediaType
                            description.text = appState.filmsData[0].overview
                            filmId=appState.filmsData[0].id


//                            title.text = film.title
//                            year.text = film.releaseDate
//                            mediaType.text = film.mediaType
//                            description.text = film.overview
//                            filmId=film.id
                        }
                    }
                })
                descriptionViewModel.loadData(film.id)
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
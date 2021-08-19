package com.example.filmsreview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmsreview.databinding.FragmentDescriptionPageBinding
import com.example.filmsreview.repository.FilmsList


class DescriptionPage : Fragment() {

    private var _binding: FragmentDescriptionPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.getParcelable<FilmsList>(BUNDLE_EXTRA)
        film?.let {
            with(binding) {
                cover.setImageResource(film.film.getCover())
                title.text = film.film.getTitle()
                year.text = (film.film.getYear().toString())
                genre.text = (film.film.getGenre())
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
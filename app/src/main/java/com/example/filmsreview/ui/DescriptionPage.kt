package com.example.filmsreview.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.filmsreview.AppState
import com.example.filmsreview.databinding.FragmentDescriptionPageBinding
import com.example.filmsreview.model.DescriptionViewModel
import com.example.filmsreview.model.Film
import com.example.filmsreview.repository.rest_entities.FactDataObj
import com.example.filmsreview.repository.rest_entities.FilmsListDataObj
import com.example.filmsreview.services.*
import org.koin.androidx.viewmodel.ext.android.viewModel


const val DESCRIPTION_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DESCRIPTION_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DESCRIPTION_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DESCRIPTION_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DESCRIPTION_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DESCRIPTION_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DESCRIPTION_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DESCRIPTION_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DESCRIPTION_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"

private const val ID_INVALID = -100
private const val LOGO_PATH_INVALID = -100
private const val NAME_INVALID = "INVALID NAME"
private const val RELEASE_DATE_INVALID = -1000
private const val ORIGIN_COUNTRY_INVALID = "INVALID ORIGIN COUNTRY"
private const val GENRES_INVALID = "INVALID GENRE"
private const val VOTE_AVERAGE_INVALID = "INVALID VOTE_AVERAGE"
private const val OVERVIEW_INVALID = "INVALID OVERVIEW"

private const val PROCESS_ERROR = "Обработка ошибки"


class DescriptionPage : Fragment() {

    private var _binding: FragmentDescriptionPageBinding? = null
    private val binding get() = _binding!!
    private val descriptionViewModel: DescriptionViewModel by viewModel()
    private lateinit var film: Film

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getStringExtra(DESCRIPTION_LOAD_RESULT_EXTRA)) {
                DESCRIPTION_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DESCRIPTION_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DESCRIPTION_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DESCRIPTION_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DESCRIPTION_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DESCRIPTION_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DESCRIPTION_RESPONSE_SUCCESS_EXTRA -> renderData(
                    FilmsListDataObj(
                        FactDataObj(
                            intent.getIntExtra(ID, ID_INVALID),
                            intent.getIntExtra(LOGO_PATH, LOGO_PATH_INVALID),
                            intent.getStringExtra(NAME, NAME_INVALID),
                            intent.getIntExtra(RELEASE_DATE, RELEASE_DATE_INVALID),
                            intent.getStringExtra()
                        /*Здесь выдает ошибку, intent.getStringExtra - это сложное выражение, в то же время необходимо прписать все поля, которые есть в FactDataObj
                        вот ошибка: Smart cast to 'String' is impossible, because 'intent.getStringExtra(NAME, NAME_INVALID)' is a complex expression
                        Что в таком случае делать?*/
                        )
                    )
                )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DESCRIPTION_INTENT_FILTER))
        }
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroy()
    }

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
        getFilm(film)
    }

    private fun getFilm(film: Film?) {
        binding.loadingLayout.visibility = View.VISIBLE
        binding.descriptionView.visibility = View.INVISIBLE

        context?.let {
            it.startService(Intent(it, DescriptionService::class.java).apply {
                putExtra(ID, film?.getID())
                putExtra(LOGO_PATH, film?.getLogoPath())
                putExtra(NAME, film?.getName())
                putExtra(RELEASE_DATE, film?.getReleaseDate())
                putExtra(ORIGIN_COUNTRY, film?.getOriginCountry())
                putExtra(GENRES, film?.getGenres())
                putExtra(VOTE_AVERAGE, film?.getVoteAverahge())
                putExtra(OVERVIEW, film?.getOverView())
            })
        }
    }

    private fun renderData(filmsDTO: FilmsListDataObj) {
        binding.loadingLayout.visibility = View.GONE
        binding.descriptionView.visibility = View.VISIBLE
        val fact = filmsDTO.film
        val id: Int = filmsDTO.film.id
        val logo_path: Int = filmsDTO.film.logo_path
        val name: String = filmsDTO.film.name
        val release_date: Int = filmsDTO.film.release_date
        val origin_country: String = filmsDTO.film.origin_country
        val genres: String = filmsDTO.film.genres
        val vote_average: Double = filmsDTO.film.vote_average
        val overview: String = filmsDTO.film.overview
        if (id == 0 ||
            logo_path == 0 ||
            name == null ||
            release_date == 0 ||
            origin_country == null ||
            genres == null ||
            vote_average == 0.0 ||
            overview == null
        ) {
            TODO(PROCESS_ERROR)
        } else {
            val filmOnDisplay = film
            with(binding) {
                title.text = filmOnDisplay.getID().toString()
                year.text = filmOnDisplay.getReleaseDate().toString()
                genre.text = filmOnDisplay.getGenres()
                description.text = filmOnDisplay.getOverView()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

//        val film = arguments?.getParcelable<Film>(BUNDLE_EXTRA)
//        film?.let {
//            with(binding) {
//
//                descriptionViewModel.liveDataToDescribe.observe(viewLifecycleOwner, { appState ->
//                    when (appState) {
//                        is AppState.Error -> {
//                            descriptionView.visibility = View.INVISIBLE
//                            loadingLayout.visibility = View.GONE
//                            errorTextView.visibility = View.VISIBLE
//                        }
//                        AppState.Loading -> {
//                            binding.loadingLayout.visibility = View.VISIBLE
//                            descriptionView.visibility = View.INVISIBLE
//                        }
//                        is AppState.Success -> {
//                            loadingLayout.visibility = View.GONE
//                            descriptionView.visibility = View.VISIBLE
//                            appState.filmsData[0].getLogoPath()
//                                ?.let { it1 -> cover.setImageResource(it1) }
//                            title.text = appState.filmsData[0].getName().toString()
//                            year.text = appState.filmsData[0].getReleaseDate().toString()
//                            genre.text = appState.filmsData[0].getGenres()
//                            description.text = appState.filmsData[0].getOverView()
//                        }
//                    }
//                })
//                descriptionViewModel.loadData("0bca8a77230116b8ac43cd3b8634aca9", id)
//            }
//        }
//    }


    companion object {
        const val BUNDLE_EXTRA = "film"
        fun newInstance(bundle: Bundle): DescriptionPage {
            val fragment = DescriptionPage()
            fragment.arguments = bundle
            return fragment
        }
    }
}
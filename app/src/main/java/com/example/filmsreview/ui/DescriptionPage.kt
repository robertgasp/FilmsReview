package com.example.filmsreview.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.filmsreview.AppState
import com.example.filmsreview.R
import com.example.filmsreview.databinding.FragmentDescriptionPageBinding
import com.example.filmsreview.model.ClickToSaveComments
import com.example.filmsreview.model.DescriptionViewModel
import com.example.filmsreview.model.FilmsRepositoryInterface
import com.example.filmsreview.model.HistoryViewModel
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.example.filmsreview.repository.rest.rest_entities.FactDataObjForDB
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class DescriptionPage : Fragment() {

    private var _binding: FragmentDescriptionPageBinding? = null
    private val binding get() = _binding!!
    private val descriptionViewModel: DescriptionViewModel by viewModel()
    private var filmId: Int? = null
    private var clickToSaveComments: ClickToSaveComments? = null

    private lateinit var factDataObjForDB: FactDataObjForDB
    val repositoryInterface: FilmsRepositoryInterface? = null
    private var userComments: String? = null
    private val historyViewModel: HistoryViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickToSaveComments) {
            clickToSaveComments = context
        }
    }

    override fun onDetach() {
        clickToSaveComments = null
        super.onDetach()
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
        val film = arguments?.getParcelable<FactDataObj>(BUNDLE_EXTRA)
        var fragmentManager = requireActivity().supportFragmentManager

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
                        is AppState.SuccessDB -> {
                            loadingLayout.visibility = View.GONE
                            descriptionView.visibility = View.VISIBLE

                            appState.filmsData?.let {
                                Picasso
                                    .get()
                                    .load("https://image.tmdb.org/t/p/original" + appState.filmsData.posterPath)
                                    .fit()
                                    .into(cover)

                                title.text = appState.filmsData.title
                                year.text = appState.filmsData.releaseDate
                                mediaType.text = appState.filmsData.mediaType
                                description.text = appState.filmsData.overview
                                filmId = appState.filmsData.id
                            }

                            appState.filmsDataDB.let {
                                factDataObjForDB = appState.filmsDataDB
                                factDataObjForDB?.let {
                                    binding.userComments.setText(appState.filmsDataDB.userComments)
                                }
//                                if (factDataObjForDB != null) {
//                                    binding.userComments.setText(appState.filmsDataDB.userComments)
//                                }
                                dataOfLastWatching.setText(appState.filmsDataDB.dateOfWatchig)
                            }

                        }
                    }
                })
                descriptionViewModel.loadData(film.id)
            }
        }



        binding.saveComments.setOnClickListener(View.OnClickListener {
            hideKeyboardFrom(requireContext(),view)
            val userComments = binding.userComments.text
            val date = Date().time
            clickToSaveComments?.saveComments(
                FactDataObjForDB(
                    FactDataObj(film?.id),
                    date.toString(),
                    userComments.toString()
                )
            )
            Log.i("userComment", "=" + factDataObjForDB.userComments)
            Toast.makeText(context, userComments, Toast.LENGTH_SHORT).show()
        })

        binding.shareFilm.setOnClickListener(View.OnClickListener {
            fragmentManager.beginTransaction()
                .replace(R.id.container,ContactsDialogFragment())
                .addToBackStack("")
                .commitAllowingStateLoss()
        })
    }


    fun hideKeyboardFrom(context: Context, view: View?) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
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
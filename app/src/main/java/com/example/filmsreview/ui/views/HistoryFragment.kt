package com.example.filmsreview.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsreview.databinding.FragmentHistoryBinding
import com.example.filmsreview.model.data.AppState
import com.example.filmsreview.model.mapper.FilmDtoMapper
import com.example.filmsreview.room.entity.HistoryEntity
import com.example.filmsreview.ui.recyclerViewAdapters.HistoryAdapter
import com.example.filmsreview.ui.viewModels.HistoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var historyAdapter: HistoryAdapter? = null
    private val historyViewModel: HistoryViewModel by viewModel()
    private var historyRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        historyRecyclerView = binding.historyRecyclerView
        return binding.root
    }

    override fun onDetach() {
        historyAdapter?.historyClickListener = null
        historyAdapter?.removeListener()
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        historyViewModel.getHistoryLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        historyViewModel.getHistoryList()
    }

    private fun initRecyclerView() {
        val lm = LinearLayoutManager(context)
        historyRecyclerView?.layoutManager = lm
        historyAdapter = HistoryAdapter()
        historyRecyclerView?.adapter = historyAdapter
        historyAdapter?.historyClickListener =
            HistoryAdapter.HistoryClickListener { historyEntity ->
                historyClicked(historyEntity)
            }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.HistorySuccess ->
                historyAdapter?.setHistory(appState.historyData)
            is AppState.Error -> {}
            else -> {}
        }
    }


    private fun historyClicked(historyEntity: HistoryEntity) {

        val film = FilmDtoMapper.historyEntityToFilmObject(historyEntity)
        val action =
            HistoryFragmentDirections.actionHistoryPageFragmentToDetailsPageFragment(movie = film)
        view?.findNavController()?.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
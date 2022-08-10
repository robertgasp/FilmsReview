package com.example.filmsreview.ui.recyclerViewAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsreview.BuildConfig
import com.example.filmsreview.R
import com.example.filmsreview.databinding.HistoryCardMaketBinding
import com.example.filmsreview.model.mapper.FilmDtoMapper
import com.example.filmsreview.room.entity.HistoryEntity
import com.squareup.picasso.Picasso

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var historyClickListener: HistoryClickListener? = null
    private var historyData: List<HistoryEntity> = listOf()
    private val imageSourcePath: String = BuildConfig.IMAGE_SOURCE_PATH


    @SuppressLint("NotifyDataSetChanged")
    fun setHistory(historyList: List<HistoryEntity>) {
        historyData = historyList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryCardMaketBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HistoryViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyData[position], holder.getBinding())
    }

    override fun getItemCount(): Int {
        return historyData.size
    }

    inner class HistoryViewHolder(itemView: View, binding: HistoryCardMaketBinding) :
        RecyclerView.ViewHolder(itemView) {
        private var binding2 = binding

        fun bind(singleHistoryFilm: HistoryEntity, field: HistoryCardMaketBinding) = with(field) {

            Picasso
                .get()
                .load(
                    imageSourcePath + FilmDtoMapper.historyEntityToFilmObject(
                        singleHistoryFilm
                    ).posterPath
                )
                .resizeDimen(R.dimen.film_cover_width, R.dimen.film_cover_height)
                .centerInside()
                .into(historyFilmImage)

            historyFilmTitle.text = FilmDtoMapper.historyEntityToFilmObject(singleHistoryFilm).title

            historyDateRequest.text = singleHistoryFilm.dateOfWatching

            root.setOnClickListener {
                historyClickListener?.onHistoryClickListener(historyData[adapterPosition])
            }
        }

        fun getBinding(): HistoryCardMaketBinding {
            return binding2
        }
    }

    fun interface HistoryClickListener {
        fun onHistoryClickListener(historyEntity: HistoryEntity)
    }

    fun removeListener() {
        historyClickListener = null
    }
}
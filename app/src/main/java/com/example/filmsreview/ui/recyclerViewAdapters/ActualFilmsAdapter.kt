package com.example.filmsreview.ui.recyclerViewAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsreview.BuildConfig
import com.example.filmsreview.R
import com.example.filmsreview.application.App
import com.example.filmsreview.databinding.FilmCardMaketBinding
import com.example.filmsreview.model.repository.Film
import com.example.filmsreview.model.mapper.FilmDtoMapper
import com.example.filmsreview.room.service.CacheFilmService
import com.example.filmsreview.room.service.CacheFilmServiceImpl
import com.example.filmsreview.room.service.HistoryServiceImpl
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ActualFilmsAdapter() : RecyclerView.Adapter<ActualFilmsAdapter.ActualFilmsHolder>() {

    private var filmData: List<Film> = listOf()
    var filmClickListener: FilmClickListener? = null
    private var cacheFilmService: CacheFilmService =
        CacheFilmServiceImpl(App.getCacheDao(), App.getFavoriteFilmDao())
    private var imageSourcePath: String = BuildConfig.IMAGE_SOURCE_PATH


    @SuppressLint("NotifyDataSetChanged")
    fun setFilm(films: List<Film>) {
        filmData = films
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActualFilmsHolder {

        val binding = FilmCardMaketBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ActualFilmsHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ActualFilmsHolder, position: Int) {
        holder.bind(filmData[position], holder.getBinding())
    }


    override fun getItemCount(): Int {
        return filmData.size
    }

    inner class ActualFilmsHolder(itemView: View, binding: FilmCardMaketBinding) :
        RecyclerView.ViewHolder(itemView) {
        var binding2 = binding
        // get() = field

        fun bind(film: Film, field: FilmCardMaketBinding) = with(field) {
            Picasso
                .get()
                .load(imageSourcePath + film.posterPath)
                .resizeDimen(R.dimen.film_cover_width, R.dimen.film_cover_height)
                .centerInside()
                .into(cover)
            title.text = film.title
            year.text = film.releaseDate

            if (film.mediaType == "movie") {
                mediaType.text = "Фильм"
            } else mediaType.text = film.mediaType
            root.setOnClickListener {
                filmClickListener?.onFilmClicked(filmData[adapterPosition])
                val historyService = HistoryServiceImpl(App.getHistoryDao())
                historyService.historyInsert(
                    FilmDtoMapper.filmEntityToHistoryEntity(
                        filmData[adapterPosition],
                        getDate()
                    )
                )
            }
            cacheFilmService.saveFilmToCache(FilmDtoMapper.filmToFilmDto(film))
        }


        fun getBinding(): FilmCardMaketBinding {
            return binding2
        }

        private fun getDate(): String {
            val currentDate = Date()
            // Форматирование времени как "день.месяц.год"
            val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val dateText: String = dateFormat.format(currentDate)
            // Форматирование времени как "часы:минуты:секунды"
            val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val timeText: String = timeFormat.format(currentDate)
            return "Дата запроса: $dateText \nВремя запроса: $timeText"
        }
    }

    fun interface FilmClickListener {
        fun onFilmClicked(film: Film)
    }

    fun removeListener() {
        filmClickListener = null
    }
}
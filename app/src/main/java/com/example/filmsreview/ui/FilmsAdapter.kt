package com.example.filmsreview.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsreview.AppState
import com.example.filmsreview.FilmClickListener
import com.example.filmsreview.databinding.FilmCardMaketBinding
import com.example.filmsreview.repository.FilmsList
import com.google.android.material.snackbar.Snackbar

class FilmsAdapter(
    private var fragment: Fragment, films: List<FilmsList>

) : RecyclerView.Adapter<FilmsAdapter.FilmsHolder>() {

    private var filmData: List<FilmsList> = listOf()
    private lateinit var binding: FilmCardMaketBinding
    private var filmClickListener: FilmClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setFilm(films: List<FilmsList>) {

        filmData = films
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsHolder {
        binding = FilmCardMaketBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FilmsHolder(binding.root)
    }

    override fun onBindViewHolder(holder: FilmsHolder, position: Int) {
        holder.bind(filmData[position])
    }

    override fun getItemCount(): Int {
        return filmData.size
    }

    fun setOnFilmClickListener(filmClickListenerFromMainPage: FilmClickListener) {
        this.filmClickListener = filmClickListenerFromMainPage
    }

    fun onFilmClickListener(film: FilmsList) {
        filmClickListener?.let {
            filmClickListener?.filmClicked(film)
        }
    }

    inner class FilmsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(film: FilmsList) = with(binding) {
            cover.setImageResource(film.film.getCover())
            title.text = film.film.getTitle()
            year.text = film.film.getYear().toString()
            genre.text = film.film.getGenre()
            root.setOnClickListener { onFilmClickListener(filmData[adapterPosition]) }
        }
    }
}
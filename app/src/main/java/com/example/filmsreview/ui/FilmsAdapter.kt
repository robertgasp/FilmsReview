package com.example.filmsreview.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsreview.FilmClickListener
import com.example.filmsreview.databinding.FilmCardMaketBinding
import com.example.filmsreview.repository.FilmsList

class FilmsAdapter(
    private var filmClickListener: FilmClickListener

) : RecyclerView.Adapter<FilmsAdapter.FilmsHolder>() {

    private var filmData: List<FilmsList> = listOf()
    private lateinit var binding: FilmCardMaketBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setFilm(setData: List<FilmsList>) {
        filmData = setData
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

    inner class FilmsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(film: FilmsList) = with(binding) {
            cover.setImageResource(film.film.getCover())
            title.text = film.film.getTitle()
            year.text = film.film.getYear().toString()
            genre.text = film.film.getGenre()
            root.setOnClickListener() { filmClickListener.filmClicked(film) }
        }
    }
}
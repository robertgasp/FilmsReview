package com.example.filmsreview.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsreview.FilmClickListener
import com.example.filmsreview.databinding.FilmCardMaketBinding
import com.example.filmsreview.model.Film
import com.example.filmsreview.repository.FilmsList

class FilmsAdapter(
    private var fragment: Fragment

) : RecyclerView.Adapter<FilmsAdapter.FilmsHolder>() {

    private var filmData: List<Film> = listOf()
    private var filmClickListener: FilmClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setFilm(films: List<Film>) {
        filmData = films
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsHolder {

       val binding = FilmCardMaketBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FilmsHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: FilmsHolder, position: Int) {
        holder.bind(filmData[position],holder.getBinding())
    }


    override fun getItemCount(): Int {
        return filmData.size
    }

    fun setOnFilmClickListener(filmClickListenerFromMainPage: FilmClickListener) {
        this.filmClickListener = filmClickListenerFromMainPage
    }

    fun onFilmClickListener(film: Film) {
        filmClickListener?.let {
            filmClickListener?.filmClicked(film)
        }
    }

    inner class FilmsHolder(itemView: View, binding: FilmCardMaketBinding) : RecyclerView.ViewHolder(itemView) {
         var binding2 = binding
            get() = field

        fun bind(film: Film, field:FilmCardMaketBinding) = with(field) {
            film.getLogoPath()?.let { cover.setImageResource(it) }
            title.text = film.getName()
            year.text = film.getReleaseDate().toString()
            genre.text = film.getGenres()
            root.setOnClickListener { onFilmClickListener(filmData[adapterPosition]) }
        }


            fun getBinding(): FilmCardMaketBinding {
                val field2=binding2
                return field2
            }
    }
}
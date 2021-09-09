package com.example.filmsreview.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsreview.FilmClickListener
import com.example.filmsreview.databinding.FilmCardMaketBinding
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.squareup.picasso.Picasso

class FilmsAdapter(
    private var fragment: Fragment
) : RecyclerView.Adapter<FilmsAdapter.FilmsHolder>() {

    private var filmData: List<FactDataObj> = listOf()
    private var filmClickListener: FilmClickListener? = null
    private var newLine:List<FactDataObj> = listOf()


    @SuppressLint("NotifyDataSetChanged")
    fun setFilm(films: List<FactDataObj>) {
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
        holder.bind(filmData[position], holder.getBinding())
       // Picasso.get().load(filmData[position].posterPath).into(holder.binding2.cover);
    }


    override fun getItemCount(): Int {
        return filmData.size
    }

    fun setOnFilmClickListener(filmClickListenerFromMainPage: FilmClickListener) {
        this.filmClickListener = filmClickListenerFromMainPage
    }

    fun onFilmClickListener(film: FactDataObj) {
        filmClickListener?.let {
            filmClickListener?.filmClicked(film)
        }
    }


    inner class FilmsHolder(itemView: View, binding: FilmCardMaketBinding) :
        RecyclerView.ViewHolder(itemView) {
        var binding2 = binding
            get() = field

        fun bind(film: FactDataObj, field: FilmCardMaketBinding) = with(field) {
            //  cover.  setImageResource(Picasso.get().load(film.logoPath))
            // film.logoPath.let { cover.setImageResource(it) }
            Picasso
                .get()
                .load(film.posterPath)
                .fit()
                .into(cover)
            title.text = film.title
            year.text = film.releaseDate

            if (film.mediaType == "movie") {
                mediaType.text = "Фильм"
            }else mediaType.text = film.mediaType
            root.setOnClickListener { onFilmClickListener(filmData[adapterPosition]) }
        }


        fun getBinding(): FilmCardMaketBinding {
            val field2 = binding2
            return field2
        }
    }
}
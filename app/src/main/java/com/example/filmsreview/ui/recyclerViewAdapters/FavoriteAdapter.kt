package com.example.filmsreview.ui.recyclerViewAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsreview.BuildConfig
import com.example.filmsreview.R
import com.example.filmsreview.databinding.FavoriteCardMaketBinding
import com.example.filmsreview.model.repository.Film
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>(),
    CoroutineScope by MainScope() {

    var favoriteClickListener: FavoriteClickListener? = null
    private var favoriteData: MutableList<Film> = mutableListOf()

    private var imageSourcePath = BuildConfig.IMAGE_SOURCE_PATH

    @SuppressLint("NotifyDataSetChanged")
    fun setFavorite(favoriteFilmEntity: List<Film>) {
        favoriteData.clear()
        favoriteData = favoriteFilmEntity.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val binding =
            FavoriteCardMaketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(favoriteData[position], holder.getBinding())
    }

    override fun getItemCount(): Int {
        return favoriteData.size
    }

    inner class FavoriteHolder(itemView: View, binding: FavoriteCardMaketBinding) :
        RecyclerView.ViewHolder(itemView) {

        private var binding2 = binding

        fun bind(favoriteSingleFilm: Film, field: FavoriteCardMaketBinding) =
            with(field) {
                Picasso
                    .get()
                    .load(imageSourcePath + favoriteSingleFilm.posterPath)
                    .resizeDimen(R.dimen.film_cover_width, R.dimen.film_cover_height)
                    .centerInside()
                    .into(favoriteFilmImage)

                favoriteFilmTitle.text = favoriteSingleFilm.title

                favoriteReleaseDate.text = favoriteSingleFilm.releaseDate

                root.setOnClickListener {
                    favoriteClickListener?.onFavoriteClickListener(favoriteData[adapterPosition])
                }
            }


        fun getBinding(): FavoriteCardMaketBinding {
            return binding2
        }
    }

    fun interface FavoriteClickListener {
        fun onFavoriteClickListener(favoriteFilmEntity: Film)
    }

    fun removeListener() {
        favoriteClickListener = null
    }
}
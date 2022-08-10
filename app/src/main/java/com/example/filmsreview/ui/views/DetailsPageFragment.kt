package com.example.filmsreview.ui.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import com.example.filmsreview.BuildConfig
import com.example.filmsreview.R
import com.example.filmsreview.databinding.FragmentDetailsPageBinding
import com.example.filmsreview.model.repository.Film
import com.example.filmsreview.model.fireBaseDB.FirebaseDbManager
import com.example.filmsreview.room.entity.FavoriteFilmEntity
import com.example.filmsreview.utils.favouriteFilmFirebaseToFavouriteFilmEntity
import com.squareup.picasso.Picasso

class DetailsPageFragment : Fragment() {

    private var _binding: FragmentDetailsPageBinding? = null
    private val binding get() = _binding!!


    private var firebaseDbManager = FirebaseDbManager()
    private var isFavorite: Boolean? = false
    private var isFav: Boolean? = null
    private var favoriteFilmEntity: FavoriteFilmEntity? = null
    private lateinit var movie: Film
    private val imageSourcePath: String = BuildConfig.IMAGE_SOURCE_PATH


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            movie = it.getParcelable(MOVIE)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        Picasso
            .get()
            .load(imageSourcePath + movie.posterPath)
            .resizeDimen(R.dimen.image_description_size, R.dimen.image_description_size)
            .centerInside()
            .into(cover)

        title.text = movie.title
        year.text = movie.releaseDate

        if (movie.mediaType == "movie") {
            mediaType.text = "Фильм"
        } else mediaType.text = movie.mediaType

        voteAverage.text = "Рейтинг: ${movie.voteAverage}"
        description.text = movie.overview

        firebaseDbManager.getFromDB {
            val result: List<FavoriteFilmEntity> = favouriteFilmFirebaseToFavouriteFilmEntity(it)
            var counter = 0
            for (item in result) {
                if (item.id == movie.id) {
                    counter++
                }
                if (counter > 0) {
                    isFavorite = true
                    like.setImageResource(R.drawable.ic_favorite_true)
                } else {
                    isFavorite = false
                    like.setImageResource(R.drawable.favorite_false)
                }
            }
            clickToLike(isFavorite!!)
        }

        if (firebaseDbManager.auth.uid == null) {
            binding.like.visibility = View.GONE
        } else {
            binding.like.visibility = View.VISIBLE
        }

    }

    private fun clickToLike(isFavorite: Boolean) = with(binding) {
        isFav = isFavorite
        like.setOnClickListener {
            if (!isFav!!) {

                like.setImageResource(R.drawable.ic_favorite_true)
                favoriteFilmEntity = FavoriteFilmEntity(movie.id, "", true)
                firebaseDbManager.postFilmToDB(favoriteFilmEntity!!)
                isFav = true
                Toast.makeText(context, "${movie.title} добавлен в избранное", LENGTH_SHORT).show()
            } else {
                isFav = isFavorite
                like.setImageResource(R.drawable.favorite_false)
                favoriteFilmEntity = FavoriteFilmEntity(movie.id, "", false)
                firebaseDbManager.deleteFilmFromDB(favoriteFilmEntity!!)
                isFav = false
                Toast.makeText(context, "${movie.title} удален из избранного", LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        val MOVIE = "movie"
    }
}
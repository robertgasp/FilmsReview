package com.example.filmsreview.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsreview.databinding.FragmentFavouriteBinding
import com.example.filmsreview.model.repository.Film
import com.example.filmsreview.model.accountHelper.FirebaseAuthentication
import com.example.filmsreview.model.data.AppState
import com.example.filmsreview.ui.dialogs.Dialog
import com.example.filmsreview.ui.dialogs.DialogConst
import com.example.filmsreview.ui.recyclerViewAdapters.FavoriteAdapter
import com.example.filmsreview.ui.viewModels.FavoriteViewModel
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteFragment() : Fragment(), FragmentContract {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private var favoriteAdapter: FavoriteAdapter? = null
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private var favoriteRecyclerVew: RecyclerView? = null


    private var dialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        favoriteRecyclerVew = binding.favoriteRecyclerView
        return binding.root
    }

    override fun onDetach() {
        favoriteAdapter?.favoriteClickListener = null
        favoriteAdapter?.removeListener()
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        dialog = Dialog(this) {
            //листенер на закрытие окна диалога, может пригодится
            favoriteViewModel.getFavoriteList()
        }

        binding.btnAuth.setOnClickListener {
            dialog?.createSignDialog(DialogConst.SIGN_UP_STATE)
        }

        binding.btnSignIn.setOnClickListener {
            dialog?.createSignDialog(DialogConst.SIGN_IN_STATE)
        }
    }

    private fun initRecyclerView() {
        val lm = LinearLayoutManager(context)
        favoriteRecyclerVew?.layoutManager = lm
        favoriteAdapter = FavoriteAdapter()
        favoriteRecyclerVew?.adapter = favoriteAdapter
        favoriteAdapter?.favoriteClickListener = FavoriteAdapter.FavoriteClickListener { movie ->
            favoriteClicked(movie)
        }

        favoriteViewModel.getFavoriteLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        favoriteViewModel.getFavoriteList()
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.FavoriteSuccess -> {
                loadingLayout.visibility = View.GONE
                if (appState.favoriteData.isEmpty()) {
                    binding.emptyPage.visibility = View.VISIBLE
                } else {
                    binding.emptyPage.visibility = View.GONE
                    favoriteAdapter?.setFavorite(appState.favoriteData)
                }
            }
            is AppState.Loading -> {
                binding.emptyPage.visibility = View.GONE
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.emptyPage.visibility = View.GONE
                loadingLayout.visibility = View.GONE
                Toast.makeText(requireActivity(), "Unknown Error", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    private fun favoriteClicked(favoriteFilmEntity: Film) {
        val action =
            FavouriteFragmentDirections.actionFavouriteFragmentToDetailsPageFragment(movie = favoriteFilmEntity)
        view?.findNavController()?.navigate(action)
    }

    override fun onStart() {
        super.onStart()
        update(FirebaseAuthentication.mAuth.currentUser)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun update(user: FirebaseUser?) {
        if (user == null) {
            binding.btnSignIn.visibility = View.VISIBLE
            binding.btnAuth.visibility = View.VISIBLE

        } else {
            binding.btnSignIn.visibility = View.GONE
            binding.btnAuth.visibility = View.GONE
        }
    }

}
package com.miraz029.watchbox.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.miraz029.watchbox.R
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.databinding.FragmentFavoriteBinding
import com.miraz029.watchbox.utilities.KEY_IMDB_ID
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        binding.rvMovieList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }

        favoriteAdapter.clickListener = { movie ->
            val bundle = bundleOf(KEY_IMDB_ID to movie.imdbId)
            view?.findNavController()?.navigate(R.id.navigation_details, bundle)
        }

        favoriteAdapter.favoriteListener = {
            favoriteAdapter.setFavorite(it)
            favoriteViewModel.setFavorite(it)
        }

        initObservers()
        return binding.root
    }

    private fun initObservers() {
        favoriteViewModel.favoriteMovieList.observe(
            viewLifecycleOwner,
            { movies ->
                favoriteAdapter.collection = movies as MutableList<Movie>
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
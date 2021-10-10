package com.miraz029.watchbox.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.miraz029.watchbox.R
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.data.model.MovieDetails
import com.miraz029.watchbox.data.model.Status
import com.miraz029.watchbox.databinding.FragmentMovieDetailsBinding
import com.miraz029.watchbox.utilities.KEY_IMDB_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private var _binding: FragmentMovieDetailsBinding? = null

    private val binding get() = _binding!!
    private var movie: Movie? = null
    private var movieDetails: MovieDetails? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        arguments?.getString(KEY_IMDB_ID)?.let {
            movieDetailsViewModel.movieId.value = it
        }
    }

    private fun initObservers() {
        movieDetailsViewModel.movieInfo.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    if (movie == null) {
                        movie = it
                        movieDetailsViewModel.movieId.value = ""
                        movieDetailsViewModel.movieData.value = it
                    }
                }
            }
        )
        movieDetailsViewModel.movieData.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    if (movieDetails != null) {
                        movie = it
                        binding.movieInfo = it
                    }
                }
            }
        )

        movieDetailsViewModel.movieDetails.observe(
            viewLifecycleOwner,
            { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        response.data?.let {
                            movieDetails = it
                            showMovieDetails()
                        }
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                    Status.NO_INTERNET -> {
                    }
                }
            }
        )
    }

    fun showMovieDetails() {
        movieDetails?.let {
            binding.movieDetail = it
        }
        movie?.let {
            binding.movieInfo = it
        }
        binding.movieDetailViewModel = movieDetailsViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
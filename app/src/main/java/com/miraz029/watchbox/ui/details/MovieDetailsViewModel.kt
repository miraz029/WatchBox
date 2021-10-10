package com.miraz029.watchbox.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    var movieId = MutableLiveData<String>()
    var movieData = MutableLiveData<Movie>()

    var movieInfo = Transformations.switchMap(movieId) { movieId ->
        repository.getMovieInfo(movieId)
    }

    val movieDetails = Transformations.switchMap(movieData) { movie ->
        repository.getDetails(imdbId = movie.imdbId)
    }

    fun setFavorite(movie: Movie) {
        movieData.value = movie.copy(isFavorite = !movie.isFavorite)
        viewModelScope.launch {
            repository.setFavorite(movie.imdbId, !movie.isFavorite)
        }
    }
}
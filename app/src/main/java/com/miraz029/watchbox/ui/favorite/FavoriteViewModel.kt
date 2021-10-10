package com.miraz029.watchbox.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    var favoriteMovieList = repository.getFavoriteMovies()

    fun setFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.setFavorite(movie.imdbId, !movie.isFavorite)
        }
    }
}
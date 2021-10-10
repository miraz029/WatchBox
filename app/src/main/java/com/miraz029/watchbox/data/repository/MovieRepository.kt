package com.miraz029.watchbox.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.miraz029.watchbox.api.ApiResponse
import com.miraz029.watchbox.api.ApiService
import com.miraz029.watchbox.api.NetworkResource
import com.miraz029.watchbox.data.dao.MovieDao
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.data.model.MovieDetails
import com.miraz029.watchbox.data.model.MovieResponse
import com.miraz029.watchbox.data.model.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val movieDao: MovieDao,
    private val apiService: ApiService
) {

    fun searchMovies(title: String): LiveData<Resource<MovieResponse>> {
        return object :
            NetworkResource<MovieResponse>(context) {
            override fun fetchFromApi(): LiveData<ApiResponse<MovieResponse>> {
                return apiService.searchMovies(query = title)
            }
        }.asLiveData()
    }

    fun getDetails(imdbId: String): LiveData<Resource<MovieDetails>> {
        return object :
            NetworkResource<MovieDetails>(context) {
            override fun fetchFromApi(): LiveData<ApiResponse<MovieDetails>> {
                return apiService.getMovieDetails(imdbId = imdbId)
            }
        }.asLiveData()
    }

    fun getFavoriteMovies() = movieDao.getFavoriteMovies().asLiveData()

    fun getMovieInfo(imdbId : String) = movieDao.getMovieInfo(imdbId).asLiveData()

    suspend fun saveMovies(movies: List<Movie>) = movieDao.insertAll(movies)

    suspend fun setFavorite(imdbId: String, isFavorite: Boolean) =
        movieDao.setFavorite(imdbId, isFavorite)
}
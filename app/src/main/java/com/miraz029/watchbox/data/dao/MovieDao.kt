package com.miraz029.watchbox.data.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraz029.watchbox.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE isFavorite = 1 ORDER BY title")
    fun getFavoriteMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movies WHERE id like :imdbId")
    fun getMovieInfo(imdbId: String): Flow<Movie>

    @Query("UPDATE movies SET isFavorite=:isFavorite WHERE id = :imdbId")
    suspend fun setFavorite(imdbId: String, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<Movie>)
}

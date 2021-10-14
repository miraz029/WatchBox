package com.miraz029.watchbox.utility

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.data.model.MovieResponse

class AndroidTestUtility {

    fun getMovies(): List<Movie> {
        val fileContent = this::class.java.classLoader.getResource("movies.json").readText()
        val movieResponse: MovieResponse =
            Gson().fromJson(fileContent, object : TypeToken<MovieResponse>() {}.type)
        return movieResponse.movies
    }

    val testMovie = Movie(
        "tt0099674",
        "The Godfather: Part III",
        "1990",
        "movie",
        "https://m.media-amazon.com/images/M/MV5BNWFlYWY2YjYtNjdhNi00MzVlLTg2MTMtMWExNzg4NmM5NmEzXkEyXkFqcGdeQXVyMDk5Mzc5MQ@@._V1_SX300.jpg",
        false
    )
}
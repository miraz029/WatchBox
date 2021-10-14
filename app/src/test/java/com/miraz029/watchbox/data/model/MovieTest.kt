package com.miraz029.watchbox.data.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MovieTest {

    private lateinit var movie: Movie

    @Before
    fun setUp() {
        movie = Movie(
            "tt0068646",
            "The Godfather",
            "1972",
            "movie",
            "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg",
            false
        )
    }

    @Test
    fun test_default_values() {
        assertEquals("tt0068646", movie.imdbId)
        assertEquals("1972", movie.year)
    }

}
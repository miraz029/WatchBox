package com.miraz029.watchbox.data


import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.miraz029.watchbox.data.dao.MovieDao
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.utility.testMovies
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test

class WatchBoxDatabaseTest {

    private lateinit var database: WatchBoxDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, WatchBoxDatabase::class.java).build()
        movieDao = database.movieDao()
        database.movieDao().insertAll(testMovies)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testInsertMovie() = runBlocking {
        val movie = Movie(
            "tt0099674",
            "The Godfather: Part III",
            "1990",
            "movie",
            "https://m.media-amazon.com/images/M/MV5BNWFlYWY2YjYtNjdhNi00MzVlLTg2MTMtMWExNzg4NmM5NmEzXkEyXkFqcGdeQXVyMDk5Mzc5MQ@@._V1_SX300.jpg",
            false
        )
        movieDao.insert(movie)
        ViewMatchers.assertThat(movieDao.getAllMovies().first().size, equalTo(3))
    }

    @Test
    fun testFavoriteMovie() = runBlocking {
        movieDao.setFavorite("tt0099674", true)
        ViewMatchers.assertThat(movieDao.getFavoriteMovies().first().size, equalTo(1))
        movieDao.setFavorite("tt0071562", true)
        ViewMatchers.assertThat(movieDao.getFavoriteMovies().first().size, equalTo(2))
        movieDao.setFavorite("tt0071562", false)
        ViewMatchers.assertThat(movieDao.getFavoriteMovies().first().size, equalTo(1))
    }

}
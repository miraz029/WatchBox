package com.miraz029.watchbox.data


import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.miraz029.watchbox.data.dao.MovieDao
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.utility.AndroidTestUtility
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test

class WatchBoxDatabaseTest {

    private lateinit var database: WatchBoxDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var testMovie: Movie

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, WatchBoxDatabase::class.java).build()
        movieDao = database.movieDao()

        val androidTestUtility = AndroidTestUtility()
        testMovie = androidTestUtility.testMovie
        database.movieDao().insertAll(androidTestUtility.getMovies())
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testInsertMovie() = runBlocking {
        movieDao.insert(testMovie)
        ViewMatchers.assertThat(movieDao.getAllMovies().first().size, equalTo(3))
    }

    @Test
    fun testFavoriteMovie() = runBlocking {
        movieDao.setFavorite("tt0068646", true)
        ViewMatchers.assertThat(movieDao.getFavoriteMovies().first().size, equalTo(1))

        movieDao.setFavorite("tt0071562", true)
        ViewMatchers.assertThat(movieDao.getFavoriteMovies().first().size, equalTo(2))
    }

}
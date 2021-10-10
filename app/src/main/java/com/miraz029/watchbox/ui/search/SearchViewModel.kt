package com.miraz029.watchbox.ui.search

import androidx.lifecycle.*
import com.miraz029.watchbox.data.DataStoreManager
import com.miraz029.watchbox.data.DataStoreManager.Companion.SEARCH_KEY
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val datastore: DataStoreManager
) : ViewModel() {

    var searchKey = MutableLiveData<String>()
    var searchTextRequest = MutableLiveData<String>()

    val searchMovieList = Transformations.switchMap(searchTextRequest) { searchText ->
        repository.searchMovies(searchText)
    }

    fun saveMovies(movies: List<Movie>) {
        viewModelScope.launch {
            repository.saveMovies(movies)
        }
        saveDefaultSearchText()
    }

    fun getDefaultSearchText() {
        viewModelScope.launch {
            datastore.getStringData(SEARCH_KEY).collect {
                if (it.isNotEmpty()) {
                    searchKey.value = it
                }
            }
        }
    }

    private fun saveDefaultSearchText() {
        searchTextRequest.value?.let {
            viewModelScope.launch {
                datastore.storeStringData(SEARCH_KEY, it)
            }
        }
    }
}
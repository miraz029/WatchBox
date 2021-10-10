package com.miraz029.watchbox.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.miraz029.watchbox.R
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.data.model.Status
import com.miraz029.watchbox.databinding.FragmentSearchBinding
import com.miraz029.watchbox.utilities.KEY_IMDB_ID
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    lateinit var filterOptions : Array<String>
    val optionStates = booleanArrayOf(true, true)

    @Inject
    lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        filterOptions = resources.getStringArray(R.array.movie_types)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.rvMovieList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }

        searchAdapter.clickListener = { movie ->
            val bundle = bundleOf(KEY_IMDB_ID to movie.imdbId)
            view?.findNavController()?.navigate(R.id.navigation_details, bundle)
        }

        binding.ivFilter.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(R.string.filter_movie_list)
            builder.setMultiChoiceItems(filterOptions, optionStates) { _, which, isChecked ->
                optionStates[which] = isChecked
            }
            builder.setPositiveButton(R.string.ok) { _, _ ->
                searchAdapter.filterList(optionStates)
            }
            val dialog = builder.create()
            dialog.show()
        }

        binding.svMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                searchViewModel.searchTextRequest.value = query
                return false
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        if (searchViewModel.searchKey.value.isNullOrEmpty()) {
            searchViewModel.getDefaultSearchText()
        }
    }

    private fun initObservers() {
        searchViewModel.searchMovieList.observe(
            viewLifecycleOwner,
            { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        binding.progressLoading.visibility = GONE
                        response.data?.movies?.let { movies ->
                            searchAdapter.collection = movies as MutableList<Movie>
                            searchViewModel.saveMovies(movies)
                        }
                    }
                    Status.LOADING -> {
                        binding.progressLoading.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.progressLoading.visibility = GONE
                    }
                    Status.NO_INTERNET -> {
                        binding.progressLoading.visibility = View.VISIBLE
                        //TODO show view for no internet
                    }
                }
            })

        searchViewModel.searchKey.observe(viewLifecycleOwner,
            { searchKey ->
                binding.svMovie.setQuery(searchKey, false)
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
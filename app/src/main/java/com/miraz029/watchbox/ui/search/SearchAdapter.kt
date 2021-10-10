package com.miraz029.watchbox.ui.search


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miraz029.watchbox.R
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.databinding.RowMovieBinding
import com.miraz029.watchbox.utilities.loadFromUrl

import javax.inject.Inject
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class SearchAdapter @Inject constructor() : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    internal var collection: MutableList<Movie> by Delegates.observable(mutableListOf()) { _, _, _ ->
        filterList(optionStates)
    }

    private var filteredList: MutableList<Movie> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal fun filterList(options: BooleanArray) {
        optionStates = options
        val result = optionStates.all { it }
        filteredList = if (result) {
            collection
        } else {
            try {
                collection.filter {
                    (it.type == "movie" && optionStates[0]) || (it.type == "series" && optionStates[1])
                }.toList() as MutableList<Movie>
            } catch (e: Exception) {
                mutableListOf()
            }
        }
        notifyDataSetChanged()
    }

    internal var optionStates = booleanArrayOf(true, true)
    internal var clickListener: (Movie) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(filteredList[position], clickListener)

    override fun getItemCount() = filteredList.size

    class ViewHolder(private val binding: RowMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            if (movie.poster.isEmpty() || movie.poster == "N/A") {
                binding.ivPoster.setImageResource(R.color.colorAccentLight)
            } else {
                binding.ivPoster.loadFromUrl(movie.poster)
            }

            binding.tvTitle.text = movie.title
            binding.tvYear.text = movie.year
            itemView.setOnClickListener {
                clickListener(movie)
            }
        }
    }
}
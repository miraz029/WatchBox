package com.miraz029.watchbox.ui.favorite


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miraz029.watchbox.R
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.databinding.RowFavoriteMovieBinding
import com.miraz029.watchbox.utilities.loadFromUrl

import javax.inject.Inject
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class FavoriteAdapter @Inject constructor() : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    internal var collection: MutableList<Movie> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal fun setFavorite(movie: Movie) {
        val index = collection.indexOf(movie)
        collection[index] = movie.copy(isFavorite = !movie.isFavorite)
        notifyItemChanged(index)
    }

    internal var clickListener: (Movie) -> Unit = { _ -> }

    internal var favoriteListener: (Movie) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener, favoriteListener)

    override fun getItemCount() = collection.size

    class ViewHolder(private val binding: RowFavoriteMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, clickListener: (Movie) -> Unit, favoriteListener: (Movie) -> Unit) {
            if (movie.poster.isEmpty() || movie.poster == "N/A") {
                binding.ivPoster.setImageResource(R.color.colorAccentLight)
            } else {
                binding.ivPoster.loadFromUrl(movie.poster)
            }
            if (movie.isFavorite) {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }

            binding.tvTitle.text = movie.title
            binding.tvYear.text = movie.year
            itemView.setOnClickListener {
                clickListener(movie)
            }
            binding.ivFavorite.setOnClickListener {
                favoriteListener(movie)
            }
        }
    }
}
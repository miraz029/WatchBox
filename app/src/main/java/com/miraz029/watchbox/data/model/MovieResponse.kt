package com.miraz029.watchbox.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(@SerializedName("Search") val movies: List<Movie>)
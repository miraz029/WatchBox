package com.miraz029.watchbox.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetails(

    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String,

    @SerializedName("Rated")
    val rated: String,

    @SerializedName("Released")
    val released: String,

    @SerializedName("Runtime")
    val duration: String,

    @SerializedName("Genre")
    val genre: String,

    @SerializedName("Director")
    val director: String,

    @SerializedName("Writer")
    val writer: String,

    @SerializedName("Actors")
    val actors: String,

    @SerializedName("Plot")
    val plot: String,

    @SerializedName("Language")
    val language: String,

    @SerializedName("Country")
    val country: String,

    @SerializedName("Awards")
    val awards: String,

    @SerializedName("Poster")
    val poster: String,

    @SerializedName("Ratings")
    val ratings: List<Rating>,

    @SerializedName("Metascore")
    val metascore: String,

    @SerializedName("imdbRating")
    val imdbRating: String,

    @SerializedName("imdbVotes")
    val imdbVotes: String,

    @SerializedName("imdbID")
    val imdbId: String,

    @SerializedName("Type")
    val type: String,

    @SerializedName("DVD")
    val dvd: String,

    @SerializedName("BoxOffice")
    val boxOffice: String,

    @SerializedName("Production")
    val production: String,

    @SerializedName("Website")
    val website: String,

    @SerializedName("Response")
    val Response: String,
) : Parcelable
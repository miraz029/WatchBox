package com.miraz029.watchbox.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movies")
@Parcelize
data class Movie(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("imdbID")
    val imdbId: String,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String,

    @SerializedName("Type")
    val type: String,

    @SerializedName("Poster")
    val poster: String,

    var isFavorite: Boolean
) : Parcelable
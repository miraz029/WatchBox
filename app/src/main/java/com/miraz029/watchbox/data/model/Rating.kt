package com.miraz029.watchbox.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(

    @SerializedName("Source")
    val source: String,

    @SerializedName("Value")
    val value: String,

) : Parcelable
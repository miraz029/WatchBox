package com.miraz029.watchbox.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context.applicationContext)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("actors")
fun showCast(cgCast: ChipGroup, actors: String?) {
    actors?.let {
        cgCast.removeAllViews()
        for (actor in it.split(",")) {
            val chip = Chip(cgCast.context)
            chip.text = actor
            cgCast.addView(chip)
        }
    }
}

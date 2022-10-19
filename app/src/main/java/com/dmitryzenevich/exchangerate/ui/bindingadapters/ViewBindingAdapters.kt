package com.dmitryzenevich.exchangerate.ui.bindingadapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.dmitryzenevich.exchangerate.R

@BindingAdapter("starBackground")
fun View.setStarBackground(isLiked: Boolean) {
    background =
        if (isLiked) ContextCompat.getDrawable(context, R.drawable.ic_star_filled)
        else ContextCompat.getDrawable(context, R.drawable.ic_star_outlined)
}
package com.example.simpleproduct.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.simpleproduct.R

@BindingAdapter("app:showImage")
fun showImage(imgView: ImageView, url: String?) {
    if (url != null) {
        Glide.with(imgView.context)
            .load(url)
            .placeholder(R.drawable.img)
            .error(R.drawable.img)
            .into(imgView)
    } else {
        Glide.with(imgView.context)
            .load(R.drawable.img)
            .placeholder(R.drawable.img)
            .error(R.drawable.img)
            .into(imgView)
    }
}


@BindingAdapter("isVisible")
fun View.isVisible(boolean: Boolean) {
    isVisible = boolean
}
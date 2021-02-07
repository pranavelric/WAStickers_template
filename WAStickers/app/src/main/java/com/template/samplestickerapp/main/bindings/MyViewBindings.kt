package com.template.samplestickerapp.main.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.template.samplestickerapp.R


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {

    Glide.with(view).load(url)
            .error(R.drawable.ic_noimg).into(view)


}

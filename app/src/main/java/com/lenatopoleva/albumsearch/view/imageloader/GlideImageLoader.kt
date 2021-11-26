package com.lenatopoleva.albumsearch.view.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lenatopoleva.albumsearch.R
import com.lenatopoleva.albumsearch.model.imageloader.IImageLoader

class GlideImageLoader : IImageLoader<ImageView>{
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .asBitmap()
            .load(url)
            .placeholder(R.drawable.ic_baseline_image_search_24)
            .into(container)
    }
}
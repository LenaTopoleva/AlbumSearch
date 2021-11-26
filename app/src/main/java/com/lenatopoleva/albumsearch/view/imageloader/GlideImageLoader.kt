package com.lenatopoleva.albumsearch.view.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.lenatopoleva.albumsearch.R
import com.lenatopoleva.albumsearch.model.imageloader.IImageLoader

class GlideImageLoader : IImageLoader<ImageView>{
    override fun loadInto(url: String, container: ImageView) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(25))

        Glide.with(container.context)
            .asBitmap()
            .load(url)
            .apply(requestOptions)
            .placeholder(R.drawable.ic_baseline_image_search_24)
            .into(container)
    }
}
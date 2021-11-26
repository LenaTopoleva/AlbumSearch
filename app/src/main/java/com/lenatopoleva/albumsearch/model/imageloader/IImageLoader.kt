package com.lenatopoleva.albumsearch.model.imageloader

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}
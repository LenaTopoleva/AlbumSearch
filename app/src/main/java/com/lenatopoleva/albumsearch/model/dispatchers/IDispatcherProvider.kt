package com.lenatopoleva.albumsearch.model.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatcherProvider {
    fun io(): CoroutineDispatcher
}
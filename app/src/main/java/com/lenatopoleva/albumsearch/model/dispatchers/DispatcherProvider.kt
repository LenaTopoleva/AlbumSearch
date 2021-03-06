package com.lenatopoleva.albumsearch.model.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProvider: IDispatcherProvider {
    override fun io(): CoroutineDispatcher = Dispatchers.IO
}
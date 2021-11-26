package com.lenatopoleva.albumsearch.model.interactor

import com.lenatopoleva.albumsearch.model.data.AppState

interface IAlbumsInteractor {
    suspend fun getAlbumsByTitle(title: String, fromRemoteSource: Boolean): AppState
}
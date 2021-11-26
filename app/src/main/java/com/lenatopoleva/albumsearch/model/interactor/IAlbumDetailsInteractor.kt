package com.lenatopoleva.albumsearch.model.interactor

import com.lenatopoleva.albumsearch.model.data.AppState

interface IAlbumDetailsInteractor {
    suspend fun getAlbumTracksById(albumId: Int, fromRemoteSource: Boolean): AppState
}
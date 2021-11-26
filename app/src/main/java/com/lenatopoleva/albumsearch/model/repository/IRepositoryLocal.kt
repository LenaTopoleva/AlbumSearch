package com.lenatopoleva.albumsearch.model.repository

import com.lenatopoleva.albumsearch.model.data.AppState

interface IRepositoryLocal : IRepository {
    suspend fun saveAlbumsToDB(appState: AppState)
    suspend fun saveAlbumTracksToDB(appState: AppState)
}
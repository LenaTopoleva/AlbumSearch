package com.lenatopoleva.albumsearch.model.datasource

import com.lenatopoleva.albumsearch.model.data.AppState

interface IDataSourceLocal : IDataSource {
    suspend fun saveAlbumsToDB(appState: AppState)
    suspend fun saveAlbumTracksToDB(appState: AppState)
}
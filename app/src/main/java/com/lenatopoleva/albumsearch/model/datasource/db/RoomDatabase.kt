package com.lenatopoleva.albumsearch.model.datasource.db

import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.datasource.IDataSourceLocal

class RoomDatabase: IDataSourceLocal {
    override suspend fun saveAlbumsToDB(appState: AppState) {
    }

    override suspend fun saveAlbumTracksToDB(appState: AppState) {
    }

    override suspend fun getAlbumsByTitle(title: String): List<Media>? {
        return null
    }

    override suspend fun getAlbumDetailsById(id: Int): List<Media>? {
        return null
    }
}
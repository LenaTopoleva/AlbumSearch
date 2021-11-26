package com.lenatopoleva.albumsearch.model.repository

import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.datasource.IDataSourceLocal


class RepositoryLocal(private val dataSource: IDataSourceLocal) : IRepositoryLocal {

    override suspend fun saveAlbumsToDB(appState: AppState) {
        dataSource.saveAlbumsToDB(appState)
    }

    override suspend fun saveAlbumTracksToDB(appState: AppState) {
        dataSource.saveAlbumTracksToDB(appState)
    }

    override suspend fun getAlbumsByTitle(title: String):  List<Media>? =
        dataSource.getAlbumsByTitle(title)

    override suspend fun getAlbumTracksById(id: Int):  List<Media>? =
        dataSource.getAlbumDetailsById(id)

}
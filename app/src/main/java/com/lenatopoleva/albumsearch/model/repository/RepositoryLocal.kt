package com.lenatopoleva.albumsearch.model.repository

import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.datasource.IDataSourceLocal
import java.util.*


class RepositoryLocal(private val dataSource: IDataSourceLocal) : IRepositoryLocal {

    override suspend fun saveAlbumsToDB(appState: AppState) {
        dataSource.saveAlbumsToDB(appState)
    }

    override suspend fun saveAlbumTracksToDB(appState: AppState) {
        dataSource.saveAlbumTracksToDB(appState)
    }

    override suspend fun getAlbumsByTitle(title: String):  List<Media>? =
        dataSource.getAlbumsByTitle(title)?.sortedBy { it.collectionName.toLowerCase(Locale.ROOT) }

    override suspend fun getAlbumDetailsById(id: Int):  List<Media>? =
        dataSource.getAlbumDetailsById(id)

}
package com.lenatopoleva.albumsearch.model.repository

import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.datasource.IDataSource

class Repository (private val dataSource : IDataSource): IRepository {

    override suspend fun getAlbumsByTitle(title: String):  List<Media>? =
        dataSource.getAlbumsByTitle(title)


    override suspend fun getAlbumTracksById(id: Int):  List<Media>? =
        dataSource.getAlbumDetailsById(id)


}
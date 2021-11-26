package com.lenatopoleva.albumsearch.model.datasource

import com.lenatopoleva.albumsearch.model.data.Media

interface IDataSource {
    suspend fun getAlbumsByTitle(title: String):  List<Media>?
    suspend fun getAlbumDetailsById(id: Int):  List<Media>?
}
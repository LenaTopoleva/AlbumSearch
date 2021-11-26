package com.lenatopoleva.albumsearch.model.repository

import com.lenatopoleva.albumsearch.model.data.Media

interface IRepository {
    suspend fun getAlbumsByTitle(title: String): List<Media>?
    suspend fun getAlbumTracksById(id: Int): List<Media>?
}
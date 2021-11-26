package com.lenatopoleva.albumsearch.model.datasource.db

import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.datasource.IDataSourceLocal
import com.lenatopoleva.albumsearch.model.datasource.db.dao.AlbumsDao
import com.lenatopoleva.albumsearch.model.datasource.db.dao.TracksDao
import com.lenatopoleva.albumsearch.utils.convertAppStateDataToAlbumDbEntities
import com.lenatopoleva.albumsearch.utils.convertAppStateDataToTrackDbEntities
import com.lenatopoleva.albumsearch.utils.mapAlbumDbEntityListToMediaList
import com.lenatopoleva.albumsearch.utils.mapTrackDbEntityListToMediaList

class RoomDatabase(private val albumsDao: AlbumsDao,
                   private val tracksDao: TracksDao): IDataSourceLocal {

    override suspend fun saveAlbumsToDB(appState: AppState) {
        convertAppStateDataToAlbumDbEntities(appState)?.let{
            albumsDao.insertAll(it)
        }
    }

    override suspend fun saveAlbumTracksToDB(appState: AppState) {
        convertAppStateDataToTrackDbEntities(appState)?.let{
            tracksDao.insertAll(it)
        }
    }

    override suspend fun getAlbumsByTitle(title: String): List<Media>? {
        if (title == "") return null
        val albums = albumsDao.getAlbumsByTitle(title)
        return albums?.let { mapAlbumDbEntityListToMediaList(it) }
    }

    override suspend fun getAlbumDetailsById(id: Int): List<Media>? {
        val albumDetails: List<Media>?
        val album = albumsDao.getAlbumById(id)
        albumDetails = if(album != null){
            val tracks = tracksDao.getTracksByAlbumId(id)
            if(tracks != null) {
                mapAlbumDbEntityListToMediaList(listOf(album))
                        .plus(mapTrackDbEntityListToMediaList(tracks))
            } else {
                mapAlbumDbEntityListToMediaList(listOf(album))
            }
        } else return null
        return albumDetails
    }
}
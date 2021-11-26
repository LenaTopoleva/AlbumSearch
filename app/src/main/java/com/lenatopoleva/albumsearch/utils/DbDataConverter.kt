package com.lenatopoleva.albumsearch.utils

import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.datasource.db.entity.AlbumDbEntity
import com.lenatopoleva.albumsearch.model.datasource.db.entity.TrackDbEntity
import kotlinx.coroutines.flow.*

suspend fun convertAppStateDataToAlbumDbEntities(appState: AppState): List<AlbumDbEntity>? =
        (appState as? AppState.Success)?.data?.asFlow()?.map {
            AlbumDbEntity(
                    collectionId = it.collectionId,
                    artistName = it.artistName,
                    collectionName = it.collectionName,
                    artworkUrl100 = it.artworkUrl100,
                    country = it.country,
                    releaseDate = it.releaseDate,
                    primaryGenreName = it.primaryGenreName)
        }?.toList()

suspend fun convertAppStateDataToTrackDbEntities(appState: AppState): List<TrackDbEntity>? =
        (appState as? AppState.Success)?.data?.asFlow()?.filterNot { it.wrapperType == COLLECTION }?.map {
            TrackDbEntity(
                    collectionId = it.collectionId,
                    artistName = it.artistName,
                    collectionName = it.collectionName,
                    artworkUrl100 = it.artworkUrl100,
                    country = it.country,
                    releaseDate = it.releaseDate,
                    primaryGenreName = it.primaryGenreName,
                    trackName = it.trackName ?: "",
                    trackNumber = it.trackNumber ?: 0,
                    trackTimeMillis = it.trackTimeMillis ?: 0
            )
        }?.toList()

suspend fun mapAlbumDbEntityListToMediaList(albumDbEntities: List<AlbumDbEntity>) : List<Media> =
    albumDbEntities.asFlow().map {
        Media(
                wrapperType = COLLECTION,
                collectionId = it.collectionId,
                artistName = it.artistName,
                collectionName = it.collectionName,
                artworkUrl100 = it.artworkUrl100,
                country = it.country,
                releaseDate = it.releaseDate,
                primaryGenreName = it.primaryGenreName)
    }.toList()

suspend fun mapTrackDbEntityListToMediaList(trackDbEntities: List<TrackDbEntity>) : List<Media> =
        trackDbEntities.asFlow().map {
            Media(
                    wrapperType = TRACK,
                    collectionId = it.collectionId,
                    artistName = it.artistName,
                    collectionName = it.collectionName,
                    artworkUrl100 = it.artworkUrl100,
                    country = it.country,
                    releaseDate = it.releaseDate,
                    primaryGenreName = it.primaryGenreName,
                    trackName = it.trackName ?: "",
                    trackNumber = it.trackNumber ?: 0,
                    trackTimeMillis = it.trackTimeMillis ?: 0)
        }.toList()


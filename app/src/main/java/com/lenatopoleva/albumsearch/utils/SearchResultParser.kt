package com.lenatopoleva.albumsearch.utils

import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.data.entity.Album
import com.lenatopoleva.albumsearch.model.data.SearchResponse
import com.lenatopoleva.albumsearch.model.data.entity.Track

fun SearchResponse.mapToMediaList(): List<Media> {
    return this.results
}

fun Media.mapToAlbum(): Album {
    return Album(
        artistId = this.artistId,
        collectionId = this.collectionId,
        artistName = this.artistName,
        collectionName = this.collectionName,
        artworkUrl100 = this.artworkUrl100,
        trackCount = this.trackCount,
        primaryGenreName = this.primaryGenreName,
        country = this.country,
        releaseDate = this.releaseDate
    )
}

fun Media.mapToTrack(): Track {
    return Track(
        artistId = this.artistId,
        collectionId = this.collectionId,
        artistName = this.artistName,
        collectionName = this.collectionName,
        artworkUrl100 = this.artworkUrl100,
        trackName = this.trackName,
        trackNumber = this.trackNumber,
        trackTimeMillis = this.trackTimeMillis
    )
}

fun List<Media>.filterFromCollections(): List<Media> = this.filterNot { it.wrapperType == COLLECTION }
package com.lenatopoleva.albumsearch.model.data

data class SearchResponse (
    val resultCount: Long,
    val results: List<Media>
)

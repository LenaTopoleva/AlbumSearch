package com.lenatopoleva.albumsearch.model.data

sealed class AppState {
    data class Success(val data: List<Media>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
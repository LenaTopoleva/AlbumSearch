package com.lenatopoleva.albumsearch.model.interactor

import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.repository.IRepository
import com.lenatopoleva.albumsearch.model.repository.IRepositoryLocal

class AlbumDetailsInteractor(
    private val repositoryRemote: IRepository,
    private val repositoryLocal: IRepositoryLocal
): IAlbumDetailsInteractor {

    override suspend fun getAlbumTracksById(albumId: Int, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(repositoryRemote.getAlbumTracksById(albumId))
            repositoryLocal.saveAlbumTracksToDB(appState)
        } else {
            appState = AppState.Success(repositoryLocal.getAlbumTracksById(albumId))
        }
        return appState
    }

}
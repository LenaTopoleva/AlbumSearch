package com.lenatopoleva.albumsearch.model.interactor

import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.repository.IRepository
import com.lenatopoleva.albumsearch.model.repository.IRepositoryLocal

class AlbumsInteractor (
    private val repositoryRemote: IRepository,
    private val repositoryLocal: IRepositoryLocal
    ): IAlbumsInteractor {

    override suspend fun getAlbumsByTitle(title: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(repositoryRemote.getAlbumsByTitle(title))
            repositoryLocal.saveAlbumsToDB(appState)
        } else {
            appState = AppState.Success(repositoryLocal.getAlbumsByTitle(title))
        }
        return appState
    }

}

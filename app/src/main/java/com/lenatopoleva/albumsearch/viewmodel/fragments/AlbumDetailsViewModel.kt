package com.lenatopoleva.albumsearch.viewmodel.fragments

import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.dispatchers.IDispatcherProvider
import com.lenatopoleva.albumsearch.model.interactor.IAlbumDetailsInteractor
import com.lenatopoleva.albumsearch.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.terrakok.cicerone.Router

class AlbumDetailsViewModel(private val albumDetailsAlbumsInteractor: IAlbumDetailsInteractor,
                            private val router: Router,
                            private val dispatcherProvider: IDispatcherProvider
): BaseViewModel<AppState>() {

    override val initialState: AppState
        get() = AppState.Initial

    private val appStateFlow: StateFlow<AppState> = mutableAppStateFlow.asStateFlow()

    fun subscribe(): StateFlow<AppState> {
        return appStateFlow
    }

    fun getData(albumId: Int, isOnline: Boolean) {
        mutableAppStateFlow.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch {
            getDataFromInteractor(albumId, isOnline)
        }
    }

    private suspend fun getDataFromInteractor(albumId: Int, isOnline: Boolean) {
        withContext(dispatcherProvider.io()){
            mutableAppStateFlow.value = (albumDetailsAlbumsInteractor.getAlbumTracksById(albumId, isOnline))
        }
    }

    override fun handleError(error: Throwable) {
        mutableAppStateFlow.value = (AppState.Error(error))
        router.exit()
    }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }

}
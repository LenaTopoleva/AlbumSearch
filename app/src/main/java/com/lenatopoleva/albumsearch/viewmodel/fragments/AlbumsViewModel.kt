package com.lenatopoleva.albumsearch.viewmodel.fragments

import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.dispatchers.IDispatcherProvider
import com.lenatopoleva.albumsearch.model.interactor.IAlbumsInteractor
import com.lenatopoleva.albumsearch.navigation.Screens
import com.lenatopoleva.albumsearch.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.terrakok.cicerone.Router

class AlbumsViewModel(private val albumsAlbumsInteractor: IAlbumsInteractor,
                      private val router: Router,
                      private val dispatcherProvider: IDispatcherProvider,
): BaseViewModel<AppState>() {

    override val initialState: AppState = AppState.Initial

    private val appStateFlow: StateFlow<AppState> = mutableAppStateFlow.asStateFlow()

    fun subscribe(): StateFlow<AppState> {
        return appStateFlow
    }

    fun getData(title: String, isOnline: Boolean) {
        mutableAppStateFlow.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch {
            getDataFromInteractor(title, isOnline)
        }
    }

    private suspend fun getDataFromInteractor(title: String, isOnline: Boolean) {
        withContext(dispatcherProvider.io()){
            mutableAppStateFlow.value = (albumsAlbumsInteractor.getAlbumsByTitle(title, isOnline))
        }
    }

    override fun handleError(error: Throwable) {
        mutableAppStateFlow.value = (AppState.Error(error))
    }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun albumClicked(albumId: Int) {
        albumId.let { router.navigateTo(Screens.AlbumDetailsScreen(it)) }
    }
}
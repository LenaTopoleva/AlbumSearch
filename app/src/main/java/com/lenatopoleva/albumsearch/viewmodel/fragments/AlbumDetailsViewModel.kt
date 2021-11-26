package com.lenatopoleva.albumsearch.viewmodel.fragments

import androidx.lifecycle.LiveData
import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.dispatchers.IDispatcherProvider
import com.lenatopoleva.albumsearch.model.interactor.IAlbumDetailsInteractor
import com.lenatopoleva.albumsearch.viewmodel.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.terrakok.cicerone.Router

class AlbumDetailsViewModel(private val albumDetailsAlbumsInteractor: IAlbumDetailsInteractor,
                            private val router: Router,
                            private val dispatcherProvider: IDispatcherProvider
): BaseViewModel<AppState>() {

    private val appStateLiveData: LiveData<AppState> = mutableAppStateLiveData

    fun subscribe(): LiveData<AppState> {
        return appStateLiveData
    }

    fun getData(albumId: Int, isOnline: Boolean) {
        mutableAppStateLiveData.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch {
            getDataFromInteractor(albumId, isOnline)
        }
    }

    private suspend fun getDataFromInteractor(albumId: Int, isOnline: Boolean) {
        withContext(dispatcherProvider.io()){
            mutableAppStateLiveData.postValue(albumDetailsAlbumsInteractor.getAlbumTracksById(albumId, isOnline))
        }
    }

    override fun handleError(error: Throwable) {
        mutableAppStateLiveData.postValue(AppState.Error(error))
        router.exit()
    }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }

}
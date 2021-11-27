package com.lenatopoleva.albumsearch.viewmodel.fragments

import androidx.lifecycle.LiveData
import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.dispatchers.IDispatcherProvider
import com.lenatopoleva.albumsearch.model.interactor.IAlbumsInteractor
import com.lenatopoleva.albumsearch.navigation.Screens
import com.lenatopoleva.albumsearch.viewmodel.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.terrakok.cicerone.Router

class AlbumsViewModel(private val albumsAlbumsInteractor: IAlbumsInteractor,
                      private val router: Router,
                      private val dispatcherProvider: IDispatcherProvider
): BaseViewModel<AppState>() {

    private val appStateLiveData: LiveData<AppState> = mutableAppStateLiveData

    fun subscribe(): LiveData<AppState> {
        return appStateLiveData
    }

    fun getData(title: String, isOnline: Boolean) {
        mutableAppStateLiveData.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch {
            getDataFromInteractor(title, isOnline)
        }
    }

    private suspend fun getDataFromInteractor(title: String, isOnline: Boolean) {
        withContext(dispatcherProvider.io()){
            mutableAppStateLiveData.postValue(albumsAlbumsInteractor.getAlbumsByTitle(title, isOnline))
        }
    }

    override fun handleError(error: Throwable) {
        mutableAppStateLiveData.postValue(AppState.Error(error))
    }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun albumClicked(albumId: Int) {
        albumId.let { router.navigateTo(Screens.AlbumDetailsScreen(it)) }
    }
}
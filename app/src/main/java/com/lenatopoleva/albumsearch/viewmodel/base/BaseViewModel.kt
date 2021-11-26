package com.lenatopoleva.albumsearch.viewmodel.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lenatopoleva.albumsearch.model.data.AppState
import kotlinx.coroutines.*

abstract class BaseViewModel<T : AppState> : ViewModel() {

    val mutableAppStateLiveData: MutableLiveData<T> = MutableLiveData()

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    abstract fun handleError(error: Throwable)

    fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun backPressed(): Boolean

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

}
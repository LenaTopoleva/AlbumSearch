package com.lenatopoleva.albumsearch.viewmodel.base

import androidx.lifecycle.ViewModel
import com.lenatopoleva.albumsearch.model.data.AppState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<T : AppState> : ViewModel() {

    abstract val initialState: T

    val mutableAppStateFlow: MutableStateFlow<T> by lazy {
        MutableStateFlow(initialState)
    }

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
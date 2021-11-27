package com.lenatopoleva.albumsearch.view.base

import androidx.fragment.app.Fragment
import com.lenatopoleva.albumsearch.R
import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.utils.ui.AlertDialogFragment
import com.lenatopoleva.albumsearch.viewmodel.base.BaseViewModel

abstract class BaseFragment<T : AppState> : Fragment() {

    companion object {
        private const val DIALOG_FRAGMENT_TAG =
            "com.lenatopoleva.albumsearch.view.base.basefragment.dialogfragmenttag"
    }

    abstract val model: BaseViewModel<T>

    protected var isNetworkAvailable: Boolean = false

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                hideLoader()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_tittle_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        handleData(it)
                    }
                } ?: showAlertDialog(
                    getString(R.string.dialog_tittle_sorry),
                    getString(R.string.empty_server_response_on_success)
                )
            }
            is AppState.Loading -> {
                showLoader()
            }
            is AppState.Error -> {
                hideLoader()
                showAlertDialog(getString(R.string.error_stub), appState.error.message)
            }
        }
    }

    abstract fun hideLoader()

    abstract fun showLoader()

    abstract fun handleData(data: List<Media>)

    private fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message).show(parentFragmentManager, DIALOG_FRAGMENT_TAG)
    }

}
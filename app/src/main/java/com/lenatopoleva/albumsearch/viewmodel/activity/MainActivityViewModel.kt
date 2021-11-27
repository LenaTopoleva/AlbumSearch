package com.lenatopoleva.albumsearch.viewmodel.activity

import androidx.lifecycle.ViewModel
import com.lenatopoleva.albumsearch.navigation.Screens
import ru.terrakok.cicerone.Router

class MainActivityViewModel(private val router: Router): ViewModel() {

    var isAlertDialogHidden = false
        private set

    fun alertDialogBtnOkClicked(){
        isAlertDialogHidden = true
    }

    fun resetAlertDialogState(){
        isAlertDialogHidden = false
    }

    fun backPressed() {
        router.exit()
    }

    fun onCreate() {
        router.replaceScreen(Screens.AlbumsScreen())
    }
}
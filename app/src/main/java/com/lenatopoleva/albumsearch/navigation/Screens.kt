package com.lenatopoleva.albumsearch.navigation

import com.lenatopoleva.albumsearch.view.fragments.AlbumDetailsFragment
import com.lenatopoleva.albumsearch.view.fragments.AlbumsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class AlbumsScreen() : SupportAppScreen() {
        override fun getFragment() = AlbumsFragment.newInstance()
    }

    class AlbumDetailsScreen(val albumId: Int): SupportAppScreen() {
        override fun getFragment() = AlbumDetailsFragment.newInstance(albumId)
    }
}
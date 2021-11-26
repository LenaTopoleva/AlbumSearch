package com.lenatopoleva.albumsearch

import android.app.Application
import com.lenatopoleva.albumsearch.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(){

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, viewModelModule, navigation, mainActivity, albumsFragment, albumDetailsFragment))
        }
    }

}
package com.lenatopoleva.albumsearch.di

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.lenatopoleva.albumsearch.model.datasource.db.Database
import com.lenatopoleva.albumsearch.model.datasource.db.RoomDatabase
import com.lenatopoleva.albumsearch.model.datasource.db.dao.AlbumsDao
import com.lenatopoleva.albumsearch.model.datasource.db.dao.TracksDao
import com.lenatopoleva.albumsearch.model.datasource.network.Retrofit
import com.lenatopoleva.albumsearch.model.dispatchers.DispatcherProvider
import com.lenatopoleva.albumsearch.model.dispatchers.IDispatcherProvider
import com.lenatopoleva.albumsearch.model.imageloader.IImageLoader
import com.lenatopoleva.albumsearch.model.interactor.AlbumDetailsInteractor
import com.lenatopoleva.albumsearch.model.interactor.AlbumsInteractor
import com.lenatopoleva.albumsearch.model.interactor.IAlbumDetailsInteractor
import com.lenatopoleva.albumsearch.model.interactor.IAlbumsInteractor
import com.lenatopoleva.albumsearch.model.repository.IRepository
import com.lenatopoleva.albumsearch.model.repository.IRepositoryLocal
import com.lenatopoleva.albumsearch.model.repository.Repository
import com.lenatopoleva.albumsearch.model.repository.RepositoryLocal
import com.lenatopoleva.albumsearch.view.imageloader.GlideImageLoader
import com.lenatopoleva.albumsearch.viewmodel.activity.MainActivityViewModel
import com.lenatopoleva.albumsearch.viewmodel.fragments.AlbumDetailsViewModel
import com.lenatopoleva.albumsearch.viewmodel.fragments.AlbumsViewModel
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Provider

val application = module {
    single { Room.databaseBuilder(get(), Database::class.java, "AlbumSearchDB").build() }
    single<AlbumsDao> { get<Database>().albumsDao() }
    single<TracksDao> { get<Database>().tracksDao() }
    single<IRepository> { Repository(Retrofit()) }
    single<IRepositoryLocal> { RepositoryLocal(RoomDatabase(get(), get())) }
    single<IDispatcherProvider> { DispatcherProvider() }
    single<IImageLoader<ImageView>> { GlideImageLoader() }
}

val viewModelModule = module {
    single<MutableMap<Class<out ViewModel>, Provider<ViewModel>>> {
        val map =
            mutableMapOf(
                MainActivityViewModel::class.java to Provider<ViewModel>{MainActivityViewModel(get<Router>())},
                AlbumsViewModel::class.java to Provider<ViewModel>{
                    AlbumsViewModel (get<IAlbumsInteractor>(), get<Router>(), get<IDispatcherProvider>()) },
                AlbumDetailsViewModel::class.java to Provider<ViewModel>{
                    AlbumDetailsViewModel(get<IAlbumDetailsInteractor>(), get<Router>(), get<IDispatcherProvider>()) })
        map
    }
    single<ViewModelProvider.Factory> { ViewModelFactory(get())}
}

val navigation = module {
    val cicerone: Cicerone<Router> = Cicerone.create()
    factory<NavigatorHolder> { cicerone.navigatorHolder }
    factory<Router> { cicerone.router }
}

val mainActivity = module {
    factory { MainActivityViewModel(get<Router>()) }
}

val albumsFragment = module {
    factory { AlbumsViewModel(get(), get(), get()) }
    factory<IAlbumsInteractor> { AlbumsInteractor(get(), get()) }
}

val albumDetailsFragment = module {
    factory { AlbumDetailsViewModel(get(), get(), get()) }
    factory<IAlbumDetailsInteractor> { AlbumDetailsInteractor(get(), get()) }
}
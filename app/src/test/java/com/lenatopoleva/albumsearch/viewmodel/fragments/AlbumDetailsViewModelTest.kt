package com.lenatopoleva.albumsearch.viewmodel.fragments

import com.lenatopoleva.albumsearch.TestCoroutineRule
import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.dispatchers.IDispatcherProvider
import com.lenatopoleva.albumsearch.model.interactor.IAlbumDetailsInteractor
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.terrakok.cicerone.Router


class AlbumDetailsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var model: AlbumDetailsViewModel

    private val testMediaList = listOf<Media>(
        Media(
            wrapperType = "collection",
            artistId = 1,
            collectionId = 1,
            artistName = "Beatles",
            collectionName = "Yellow Submarine",
            artworkUrl100 = ""
        )
    )
    private val testAppStateSuccess = AppState.Success(testMediaList)
    private val testError = Throwable("test error")
    private val testAppStateError = AppState.Error(testError)

    @Mock lateinit var router: Router
    @Mock lateinit var interactor: IAlbumDetailsInteractor
    @Mock lateinit var dispatcherProvider: IDispatcherProvider

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this);
        model = AlbumDetailsViewModel(interactor, router, dispatcherProvider)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getData_method_change_mutableAppStateFlow_value(){
        testCoroutineRule.runBlockingTest {
            `when`(dispatcherProvider.io()).thenReturn(testCoroutineRule.testCoroutineDispatcher)
            `when`(interactor.getAlbumTracksById(Mockito.anyInt(), Mockito.anyBoolean()))
                .thenReturn(testAppStateSuccess)
            model.getData(1, true)
            Assert.assertEquals(testAppStateSuccess, model.mutableAppStateFlow.value)
        }
    }

    @Test
    fun handleError_method_change_mutableAppStateFlow_value(){
        model.handleError(testError)
        Assert.assertEquals(testAppStateError, model.mutableAppStateFlow.value)
    }

    @Test
    fun handleError_method_invokes_router_exit(){
        model.handleError(testError)
        verify(router, times(1)).exit()
    }

    @Test
    fun backPressed_method_invokes_router_exit(){
        model.backPressed()
        verify(router, times(1)).exit()
    }

}
package com.lenatopoleva.albumsearch.viewmodel.fragments

import com.lenatopoleva.albumsearch.TestCoroutineRule
import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.dispatchers.IDispatcherProvider
import com.lenatopoleva.albumsearch.model.interactor.IAlbumsInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import ru.terrakok.cicerone.Router


class AlbumsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var model: AlbumsViewModel

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
    private val testAlbumId = 1

    @Mock lateinit var router: Router
    @Mock lateinit var interactor: IAlbumsInteractor
    @Mock lateinit var dispatcherProvider: IDispatcherProvider


    @Before
    fun setup() {
        initMocks(this);
        model = AlbumsViewModel(interactor, router, dispatcherProvider)
    }

    @Test
    fun appState_is_initial_before_getData_method(){
        Assert.assertEquals(AppState.Initial, model.mutableAppStateFlow.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getData_method_change_mutableAppStateFlow_value() {
        testCoroutineRule.runBlockingTest {
            `when`(interactor.getAlbumsByTitle(anyString(), anyBoolean()))
                .thenReturn(testAppStateSuccess)
            `when`(dispatcherProvider.io()).thenReturn(testCoroutineRule.testCoroutineDispatcher)
            model.getData("Some album", true)
            Assert.assertEquals(testAppStateSuccess, model.mutableAppStateFlow.value)
        }
    }

    @Test
    fun handleError_method_changes_appState_to_Error(){
        model.handleError(testError)
        Assert.assertEquals(testAppStateError, model.mutableAppStateFlow.value)
    }

    @Test
    fun backPressed_method_invokes_router_exit(){
        model.backPressed()
        verify(router, times(1)).exit()
    }

    @Test
    fun albumClicked_method_invokes_router_navigateTo(){
        model.albumClicked(testAlbumId)
        verify(router, times(1)).navigateTo(any())
    }
}
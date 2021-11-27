package com.lenatopoleva.albumsearch.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lenatopoleva.albumsearch.R
import com.lenatopoleva.albumsearch.databinding.ActivityMainBinding
import com.lenatopoleva.albumsearch.utils.network.isOnline
import com.lenatopoleva.albumsearch.utils.ui.AlertDialogFragment
import com.lenatopoleva.albumsearch.utils.ui.BackButtonListener
import com.lenatopoleva.albumsearch.viewmodel.activity.MainActivityViewModel
import org.koin.android.ext.android.getKoin
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity() {

    companion object {
        private const val DIALOG_FRAGMENT_TAG =
                "com.lenatopoleva.albumsearch.view.activity.mainactivity.dialogfragmenttag"
    }

    private val navigatorHolder: NavigatorHolder by lazy { getKoin().get<NavigatorHolder>() }
    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    val model: MainActivityViewModel by lazy {
        ViewModelProvider(this, getKoin().get()).get(MainActivityViewModel::class.java)
    }
    private lateinit var binding: ActivityMainBinding

    var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Here navigate to AlbumsFragment
        if(savedInstanceState == null) model.onCreate()

        // Show internet connectivity status when app starts (if no internet)
        isNetworkAvailable = isOnline(applicationContext)
        if (!isNetworkAvailable && isDialogNull() && savedInstanceState == null) {
            showNoInternetConnectionDialog()
        }
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    private fun showNoInternetConnectionDialog() {
        showAlertDialog(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
        )
    }

    private fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message).show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        model.backPressed()
    }

}
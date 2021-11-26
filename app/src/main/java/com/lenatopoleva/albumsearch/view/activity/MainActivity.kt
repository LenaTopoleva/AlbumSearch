package com.lenatopoleva.albumsearch.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lenatopoleva.albumsearch.R
import com.lenatopoleva.albumsearch.databinding.ActivityMainBinding
import com.lenatopoleva.albumsearch.utils.ui.BackButtonListener
import com.lenatopoleva.albumsearch.viewmodel.activity.MainActivityViewModel
import org.koin.android.ext.android.getKoin
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity() {

    private val navigatorHolder: NavigatorHolder by lazy { getKoin().get<NavigatorHolder>() }
    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    val model: MainActivityViewModel by lazy {
        ViewModelProvider(this, getKoin().get()).get(MainActivityViewModel::class.java)
    }
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null) model.onCreate()
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
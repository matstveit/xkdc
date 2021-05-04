package com.example.xkdcapp.screens

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.xkdcapp.Constants.IMAGE_CACHE_DIRECTORY
import com.example.xkdcapp.R
import com.example.xkdcapp.screens.common.activities.BaseActivity
import com.example.xkdcapp.utils.CacheProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject lateinit var cacheProvider: CacheProvider
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navController = findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onDestroy() {
        cacheProvider.clearCacheDirectory(IMAGE_CACHE_DIRECTORY)
        super.onDestroy()
    }
}
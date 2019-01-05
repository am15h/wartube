package com.amishgarg.wartube.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.amishgarg.wartube.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.*
import com.amishgarg.wartube.ViewModels.PostDetailViewModel
import com.amishgarg.wartube.ViewModels.PostDetailViewModelFactory

class MainActivity : androidx.appcompat.app.AppCompatActivity() {

    //todo: loading dialogs wherever required
    //    //todo: Material UI
    //    //todo: Add Likes section
    //    //todo: Add comments section
    //    //todo: Navigation, back stack, toolbar, animations
    //    //todo: MVVM to NewPostFragment,
    //    //todo: Solve Item Repeating bug
    //    //todo: replace handler, runnable with something, else in YoutubeRepository
    //    //todo: Add room
    //    //todo: Add Notification system for new post
    //    //todo: display full image when clicked on image

    //todo: Most imp todo Welcome Activity backstakc null

    // private val GOOGLE_YOUTUBE_API_KEY = "AIzaSyBV4XQEZ9l1HZeBQFL6ZZvHYfMhtnqUkmw"
    // private val CHANNEL_ID_TS = "UCq-Fj5jknLsUf-MWSy4_brA"
    // private val CHANNEL_ID_PDP = "UC-lHJZR3Gqxm24_Vd_AJ5Yw"
    // var SUBS_TS = 0
    // var SUBS_PDP = 0

    companion object {

        lateinit var host: NavHostFragment
        lateinit var navController: NavController

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Toolbar and host and navController
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

         host = supportFragmentManager
                .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

         navController = host.navController

         setupBottomNav(navController)

    }


    private fun setupBottomNav(navController: NavController)
    {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }
}

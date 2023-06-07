package com.htetarkarzaw.twitterlite.ui

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.databinding.ActivityMainBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navController: NavController
    private val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        when (destination.id) {
            R.id.feedFragment -> {
                navController.graph.setStartDestination(destination.id)
                shouldShowBottomNavigation(
                    true
                )
            }
            R.id.settingFragment->{
                shouldShowBottomNavigation(
                    true
                )
            }
            else -> shouldShowBottomNavigation(false)
        }
        Timber.tag("hakz.navigation").d("Controller->$controller/Destination->$destination/Argument->$arguments")
    }

    override fun initUi() {
        setUpNavigation()
        binding.fab.setOnClickListener {
            when (navController.currentDestination?.id) {
                R.id.feedFragment -> {
                    navController.navigate(R.id.action_feedFragment_to_uploadFeedFragment)
                }

                R.id.settingFragment -> {
                    navController.navigate(R.id.action_settingFragment_to_uploadFeedFragment)
                }
            }
        }
    }

    override fun observe() {
    }


    private fun shouldShowBottomNavigation(flag: Boolean) {
        if (flag) {
            binding.bottomAppBar.visibility = View.VISIBLE
            binding.fab.visibility = View.VISIBLE
        } else {
            binding.fab.visibility = View.GONE
            binding.bottomAppBar.visibility = View.GONE
        }
    }

    private fun setUpNavigation() {
        val navView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(listener)
        super.onPause()
    }
}
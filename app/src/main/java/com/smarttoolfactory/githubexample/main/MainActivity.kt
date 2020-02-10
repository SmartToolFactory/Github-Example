package com.smarttoolfactory.githubexample.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.smarttoolfactory.common.observe
import com.smarttoolfactory.githubexample.R
import com.smarttoolfactory.githubexample.base.activity.BaseActivity
import com.smarttoolfactory.githubexample.databinding.ActivityMainBinding

/**
 * 🔥 MainActivity has toolbar to demonstrate Toolbar with Navigation components and
 * ViewModel that is shared by both Activity and Fragment.
 *
 * Building fragment layouts with their own Toolbar is simpler than this implementation.
 */
class MainActivity : BaseActivity<ActivityMainBinding, ToolbarVM>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override val viewModelClass: Class<ToolbarVM>
        get() = ToolbarVM::class.java

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get NavController
        val navController = findNavController(R.id.nav_host_fragment)

        setUpToolbar(navController, dataBinding.toolbar)

        listenForNavigationEvents(navController, dataBinding.toolbar)


    }


    private fun setUpToolbar(navController: NavController, toolbar: Toolbar) {
        // Set Toolbar
        setSupportActionBar(toolbar)

        // Get App Configuration from nav graph
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /**
     *
     * Listen navigation component destination changes and adjust toolbar accordingly
     */
    private fun listenForNavigationEvents(navController: NavController, toolbar: Toolbar) {


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.repo_list_dest) {
                toolbar?.title = "Home"
                dataBinding.btnFavoriteRepo.visibility = View.GONE
            } else {
                dataBinding.btnFavoriteRepo.visibility = View.VISIBLE
            }
        }

        observe(viewModel.toolbarTitle) {
            toolbar?.title = it
        }
    }

    // This function is required with appbar to handle back button
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
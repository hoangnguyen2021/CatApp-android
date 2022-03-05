package com.example.catapp

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.catapp.databinding.ActivityMainBinding
import com.example.catapp.ui.adapter.CatBreedsAdapter
import com.example.catapp.ui.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var catBreedsViewModelFactory: CatBreedsViewModelFactory
    lateinit var catBreedsViewModel: CatBreedsViewModel

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var catBreedsAdapter: CatBreedsAdapter

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // use view binding in MainActivity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set toolbar as default action bar
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        // passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // connect the drawerLayout to the navigation graph
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_home,
                R.id.fragment_saved,
                R.id.fragment_login,
                R.id.fragment_register
            ),
            drawerLayout
        )

        // retrieve the navController directly from the NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        // add navigation support to the default action bar (toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // connect navView with navController
        val navView: NavigationView = binding.navView
        navView.setupWithNavController(navController)

        // default action button's listener
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // create cat breeds view model
        catBreedsViewModel =
            ViewModelProvider(this, catBreedsViewModelFactory)[CatBreedsViewModel::class.java]
        // create login view model
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        loginViewModel.currentUser.observe(this) {
            val headerView = binding.navView.getHeaderView(0)
            headerView.findViewById<TextView>(R.id.display_name).text = it.displayName
            headerView.findViewById<TextView>(R.id.email).text = it.email
        }
    }


    /**
     *  Initializes the contents of the Activity's standard options menu.
     *
     * @param menu the options menu in which you place your items.
     *  @return true for the menu to be displayed
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /**
     *  Handles up navigation.
     *
     *  @return true if Up navigation completed successfully and this Activity
     *  was finished, false otherwise.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
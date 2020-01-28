package com.andalus.lifeachievements.ui.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.andalus.lifeachievements.R
import com.andalus.lifeachievements.models.MiniUser
import com.andalus.lifeachievements.repositories.LoggedUserRepository
import com.andalus.lifeachievements.ui.create_challenge.CreateChallengeActivity
import com.andalus.lifeachievements.ui.profile.ProfileActivity
import com.andalus.lifeachievements.utils.Constants
import com.andalus.lifeachievements.utils.NetworkStateTracer
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            startActivity(Intent(this, CreateChallengeActivity::class.java))
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = this.findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu COLUMN_ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.nav_tools,
                R.id.nav_share,
                R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        NetworkStateTracer(baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

        //---------------
        val header = nav_view.getHeaderView(0)
        var currentUser: MiniUser? = null

        homeViewModel =
            ViewModelProviders.of(this,
                HomeViewModelFactory(
                    LoggedUserRepository(this)
                )
            )
                .get(HomeViewModel::class.java)


        homeViewModel.miniUser.observe(this, Observer {
            currentUser = it
            header.tvNameNav.text = it.name
            header.tvUsernameNav.text = getString(R.string.username_placeholder, it.username)
            Glide.with(this).load(it.picture).placeholder(R.drawable.ic_man)
                .into(header.ivProfileNav)
        })

        header.ivProfileNav.setOnClickListener {
            if (currentUser!=null){
                startActivity(
                    Intent(
                        this,
                        ProfileActivity::class.java
                    ).apply { putExtra(Constants.MINI_USER_ID_KEY, currentUser?.id) })
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

package com.example.meetup

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var navController: NavController

    private val fragmentChangelistener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> hideToolbar()
                R.id.emailLoginFragment -> {
                    showToolbar()
                    hideMenuItem()
                }
                R.id.registrationFragment -> {
                    showToolbar()
                    hideMenuItem()
                }
                R.id.recyclerViewFragment -> {
                    findNavController(R.id.nav_host_fragment_container).graph.startDestination =
                        R.id.recyclerViewFragment
                    showToolbar()
                    showMenuItem()
                }
                R.id.userDetailFragment -> hideToolbar()
                R.id.mapsFragment -> {
                    hideMenuItem()
                    showToolbar()
                }
                else -> Toast.makeText(this, "Else", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mAuth = Firebase.auth
    }

    override fun onResume() {
        super.onResume()
        navController = findNavController(R.id.nav_host_fragment_container)
        navController.addOnDestinationChangedListener(fragmentChangelistener)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(fragmentChangelistener)
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                mAuth.signOut()
                navController = findNavController(R.id.nav_host_fragment_container)
                navController.graph.startDestination = R.id.loginFragment
                navController.navigate(R.id.loginFragment)
            }
            else -> Log.d("###", "ELSE")
        }
        return super.onOptionsItemSelected(item)
    }


    private fun hideToolbar() {
        toolbar.visibility = View.GONE
    }

    private fun showToolbar() {
        toolbar.visibility = View.VISIBLE
    }

    private fun hideMenuItem() {
        toolbar.menu.findItem(R.id.menu_logout).isVisible = false
    }

    private fun showMenuItem() {
        toolbar.menu.findItem(R.id.menu_logout)?.isVisible = true
    }
}

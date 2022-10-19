package com.dmitryzenevich.exchangerate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.ui.*
import com.dmitryzenevich.exchangerate.R
import com.dmitryzenevich.exchangerate.ui.utils.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.navHostFragment)
        findViewById<BottomNavigationView>(R.id.navMenu)
            .setupWithNavController(navController)

        setupActionBarWithNavController(navController, AppBarConfiguration(navController.graph))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_sort, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp(AppBarConfiguration(navController.graph))
                || super.onSupportNavigateUp()
    }
}
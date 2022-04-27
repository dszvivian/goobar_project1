package com.example.goobar_project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.goobar_project1.details.ForecastDetailsFragment
import com.example.goobar_project1.forecast.CurrentForecastFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager = TempDisplaySettingManager(this)


        //toolbar
        findViewById<Toolbar>(R.id.toolbar).setTitle(R.string.app_name)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)

        // bottom navigation view
        val navController = findNavController(R.id.nav_host_fragment)
        findViewById<BottomNavigationView>(R.id.bottomNavigation).setupWithNavController(navController)

    }
    

    // Explicit Intent to share data b/w ForecastDetailsActivity
    private fun forecastdetailsIntent(forecast:DailyForecast){
        val forecastDetailsIntent = Intent(this@MainActivity , ForecastDetailsFragment::class.java)
        forecastDetailsIntent.putExtra("key_temp",forecast.temp )
        forecastDetailsIntent.putExtra("key_desc",forecast.desc )
        startActivity(forecastDetailsIntent)
    }

    // creates a Menu Option on the Activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater  // declaration of menuInflater
        inflater.inflate(R.menu.display_settings , menu)
        return true
    }
    // sets functionality for Menu Option
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_tempDisplaySettings -> {
                showTempDisplaySettingDialog(this@MainActivity , tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




}


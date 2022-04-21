package com.example.goobar_project1

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goobar_project1.details.ForecastDetailsActivity
import com.example.goobar_project1.forecast.CurrentForecastFragment
import com.example.goobar_project1.location.LocationEntryFragment

class MainActivity : AppCompatActivity() , AppNavigator {

    val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        tempDisplaySettingManager = TempDisplaySettingManager(this)
//
//
//        val rvForecastList : RecyclerView = findViewById(R.id.rv_forecastList)
//        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){forecast ->
//            forecastdetailsIntent(forecast)
//        }
//        rvForecastList.adapter = dailyForecastAdapter
//        rvForecastList.layoutManager = LinearLayoutManager(this)
//
//
//
//
//        val weeklyForecastObserver =  Observer<List<DailyForecast>> {forecastItems ->
//            dailyForecastAdapter.submitList(forecastItems)
//        }
//
//        forecastRepository.weeklyForecast.observe(this , weeklyForecastObserver)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer , LocationEntryFragment())
            .commit()

    }

    override fun navigateToCurrentForecast(zipcode: String) {
//        forecastRepository.loadForecast(zipcode)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,CurrentForecastFragment.newnstance(zipcode))
            .commit()
    }

    override fun navigateToLocationEntry() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,LocationEntryFragment())
            .commit()
    }

    // Explicit Intent to share data b/w ForecastDetailsActivity
    private fun forecastdetailsIntent(forecast:DailyForecast){
        val forecastDetailsIntent = Intent(this@MainActivity , ForecastDetailsActivity::class.java)
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
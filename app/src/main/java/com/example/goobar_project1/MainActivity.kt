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

class MainActivity : AppCompatActivity() {

    val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etZipcode: EditText = findViewById(R.id.et_zipcode)
        val tvZipcode: TextView = findViewById(R.id.tv_zipcode)
        val btnEnter: Button = findViewById(R.id.btn_zipcode)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

        //region recycler view declarations

        val rvForecastList : RecyclerView = findViewById(R.id.rv_forecastList)
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){forecast ->
            forecastdetailsIntent(forecast)
        }
        rvForecastList.adapter = dailyForecastAdapter
        rvForecastList.layoutManager = LinearLayoutManager(this)

        //endregion

        btnEnter.setOnClickListener {

            val zipcode: String = etZipcode.text.toString()

            if(zipcode.length != 5){
                Toast.makeText(this@MainActivity, R.string.error_zipcode_entry , Toast.LENGTH_SHORT).show()
                etZipcode.text.clear()
            }else{
                forecastRepository.loadForecast(zipcode)
                etZipcode.text.clear()
            }

        }

        val weeklyForecastObserver =  Observer<List<DailyForecast>> {forecastItems ->
            dailyForecastAdapter.submitList(forecastItems)
        }

        forecastRepository.weeklyForecast.observe(this , weeklyForecastObserver)

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
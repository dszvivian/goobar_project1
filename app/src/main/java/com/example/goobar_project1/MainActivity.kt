package com.example.goobar_project1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etZipcode: EditText = findViewById(R.id.et_zipcode)
        val tvZipcode: TextView = findViewById(R.id.tv_zipcode)
        val btnEnter: Button = findViewById(R.id.btn_zipcode)

        //region recycler view declarations

        val rvForecastList : RecyclerView = findViewById(R.id.rv_forecastList)
        val dailyForecastAdapter = DailyForecastAdapter(){forecastitem ->
            val msg = getString(R.string.forecast_clicked_toast , forecastitem.temp , forecastitem.desc)
            Toast.makeText(this@MainActivity , msg , Toast.LENGTH_SHORT).show()
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
}
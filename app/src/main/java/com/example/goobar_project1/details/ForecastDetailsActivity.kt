package com.example.goobar_project1.details

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.goobar_project1.*

class ForecastDetailsActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        setTitle(R.string.forecast_details)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

        //region declarations
        val tempText = findViewById<TextView>(R.id.details_tvTemp)
        val descText = findViewById<TextView>(R.id.details_tvDesc)
        //endregion

        val temp = intent.getFloatExtra("key_temp",0f)

        tempText.text = formatTempForDisplay(temp , tempDisplaySettingManager.getTempDisplaySettings())
        descText.text = intent.getStringExtra("key_desc")

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
                showTempDisplaySettingDialog(this , tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
package com.example.goobar_project1

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatTempForDisplay(temp : Float, tempDisplaySettings: TempDisplaySettings):String{
    return when(tempDisplaySettings){
        TempDisplaySettings.Fahrenheit -> String.format("%.2f °F", temp)
        TempDisplaySettings.Celsius ->{
            val temp =  (temp -32f) * (5f/9f)
            String.format("%.2f °C", temp)
        }
    }
}

// Dialog box to choose b/w F or C
fun showTempDisplaySettingDialog(context: Context ,tempDisplaySettingManager: TempDisplaySettingManager){
    val dialogBuilder = AlertDialog.Builder(context)
        .setTitle("Choose Display Units")
        .setMessage("Choose °F or °C Temperature Units ")
        .setPositiveButton("°F"){_,_, ->  // using Lambdas
            Toast.makeText(context , "°F Selected", Toast.LENGTH_SHORT).show()
            tempDisplaySettingManager.updateSetting(TempDisplaySettings.Fahrenheit)
        }
        .setNeutralButton("°C", object : DialogInterface.OnClickListener{  // Without Using Lambda
            override fun onClick(p0: DialogInterface?, p1: Int) {
                Toast.makeText(context , "°F Selected", Toast.LENGTH_SHORT).show()
                tempDisplaySettingManager.updateSetting(TempDisplaySettings.Celsius)
            }
        })
        .setOnDismissListener{
            Toast.makeText(context , "Settings will be displayed after App Restart", Toast.LENGTH_SHORT).show()
        }

    dialogBuilder.show()
}
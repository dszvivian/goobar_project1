package com.example.goobar_project1

import android.content.Context

enum class TempDisplaySettings{
    Fahrenheit , Celsius
}

class TempDisplaySettingManager(context: Context) {

    private val prefernces = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun updateSetting(setting : TempDisplaySettings){
        prefernces.edit().putString("key_temp_display" , setting.name).commit()
    }

    fun getTempDisplaySettings():TempDisplaySettings{
        val settingValue = prefernces.getString("key_temp_display" , TempDisplaySettings.Fahrenheit.name) ?: TempDisplaySettings.Fahrenheit.name
        return TempDisplaySettings.valueOf(settingValue)
    }


}


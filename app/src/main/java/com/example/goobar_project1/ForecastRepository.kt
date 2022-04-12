package com.example.goobar_project1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

//This class contains all the data that is needed to feed the recyclerView

class ForecastRepository {

    // below two variables are Placed such that
    // Weekly Forecast can only be changed from the repository
    // But not from the main Activity

    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast : LiveData<List<DailyForecast>> = _weeklyForecast


    fun loadForecast(zipcode:String){
        val randomValues = List(45){ Random.nextFloat().rem(100) * 100 }  // random values from 0 - 100
        val forecastItems = randomValues.map{ temp -> DailyForecast(temp , gettempDescription(temp)) }

        _weeklyForecast.value = forecastItems
    }


    fun gettempDescription(temp: Float) : String{
        return when(temp){
                in Float.MIN_VALUE.rangeTo(0f) -> "Anything Below Dosen't Exist"
                in 0f.rangeTo(32f) -> "Way Too Cold"
                in 32f.rangeTo(70f) -> "Perfect weather"
                in 70f.rangeTo(80f) -> "Started Sweating"
                in 80f.rangeTo(100f) -> "Where's the A/C"
                in 100f.rangeTo(Float.MAX_VALUE) -> "What is this Arizona ?"
                else -> "Does Not Compute"
            }
    }
}
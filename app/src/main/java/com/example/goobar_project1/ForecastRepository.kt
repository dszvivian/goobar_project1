package com.example.goobar_project1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.goobar_project1.api.CurrentWeather
import com.example.goobar_project1.api.WeeklyForecast
import com.example.goobar_project1.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.random.Random

//This class contains all the data that is needed to feed the recyclerView

class ForecastRepository {

    // below two variables are Placed such that
    // Weekly Forecast can only be changed from the repository
    // But not from the main Activity

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather : LiveData<CurrentWeather> = _currentWeather

    private val _weeklyForecast = MutableLiveData<WeeklyForecast>()
    val weeklyForecast : LiveData<WeeklyForecast> = _weeklyForecast


    fun loadWeeklyForecast(zipcode:String){
        val call = createOpenWeatherMapService().currentWeather(zipcode , "imperial" , BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather>{

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName , "Error loading WeeklyWeather",t)
            }

            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>)
            {
                val weatherResponse = response.body()
                if(weatherResponse != null){
                    //load 7 Day forecast
                    val forecastCall = createOpenWeatherMapService().sevenDayForecast(
                        lat = weatherResponse.coord.lat ,
                        lon =  weatherResponse.coord.lon ,
                        exclude = "current,minutely,hourly",
                        units = "imperial",
                        apikey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )
                    forecastCall.enqueue(object : Callback<WeeklyForecast>{

                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(ForecastRepository::class.java.simpleName , "Error Loading weeekly forecast")
                        }

                        override fun onResponse(call: Call<WeeklyForecast>, response: Response<WeeklyForecast>) {
                            val weeklyForecastResponse = response.body()
                            if(weeklyForecastResponse != null){
                                _weeklyForecast.value = weeklyForecastResponse!!
                            }
                        }



                    })
                }
            }


        })
    }


    fun loadCurrentForecast(zipcode:String){
        val call = createOpenWeatherMapService().currentWeather(zipcode , "imperial" , BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather>{

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName , "Error loading CurrentWeather",t)
            }

            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>)
            {
                val weatherResponse = response.body()
                if(weatherResponse != null){
                    _currentWeather.value = weatherResponse!!
                }
            }


        })
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
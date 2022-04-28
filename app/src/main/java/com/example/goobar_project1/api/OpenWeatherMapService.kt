package com.example.goobar_project1.api

import com.example.goobar_project1.Location
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


fun createOpenWeatherMapService() : OpenWeatherMapService{
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    return retrofit.create(OpenWeatherMapService::class.java)
}

interface OpenWeatherMapService {

    @GET("/data/2.5/weather")
    fun currentWeather(
        @Query("zip") zipcode: String ,
        @Query("units") units: String,
        @Query("appid") apikey: String
    ) : Call<CurrentWeather>

    @GET("/data/2.5/onecall")
    fun sevenDayForecast(
        @Query("lat") lat : Float ,
        @Query("lon") lon : Float ,
        @Query("exclude") exclude : String ,
        @Query("units") units: String,
        @Query("appid") apikey: String
    ) : Call<WeeklyForecast>


}
package com.example.goobar_project1.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goobar_project1.*
import com.example.goobar_project1.api.CurrentWeather
import com.example.goobar_project1.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private lateinit var locationRepository: LocationRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        //code here1

        val btnLocationEntry : FloatingActionButton = view.findViewById(R.id.btn_locationEntry)

        btnLocationEntry.setOnClickListener{
            showLocationEntry()
        }

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""





        val locationName: TextView = view.findViewById(R.id.tv_currentLocationName)
        val tempText : TextView = view.findViewById(R.id.tv_currentTemp)

        val currentWeatherObserver = Observer<CurrentWeather> {weather ->
            //todo : update our list Adapter
            locationName.text = weather.name
            tempText.text = formatTempForDisplay(weather.forecast.temp , tempDisplaySettingManager.getTempDisplaySettings())
        }
        forecastRepository.currentWeather.observe(viewLifecycleOwner,currentWeatherObserver)

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location>{saveLocation ->
            when(saveLocation){
                is Location.Zipcode -> forecastRepository.loadCurrentForecast(saveLocation.zipcode)
            }
        }

        locationRepository.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)

        return view
    }

    private fun showLocationEntry(){
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    //todo:clean alll the comments and delete showForecastDetails which is not needed
//    private fun showForecastDetails(forecast: DailyForecast){
//        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment(forecast.temp,forecast.desc)
//        findNavController().navigate(action)
//    }

    companion object{
        const val KEY_ZIPCODE = "key_zipcode"

        fun newnstance(zipcode:String):CurrentForecastFragment {
            val fragment = CurrentForecastFragment()

            val args = Bundle()
            args.getString(KEY_ZIPCODE,zipcode)
            fragment.arguments = args

            return fragment
        }
    }


}
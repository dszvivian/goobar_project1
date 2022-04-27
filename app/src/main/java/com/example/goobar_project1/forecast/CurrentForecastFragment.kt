package com.example.goobar_project1.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goobar_project1.*
import com.example.goobar_project1.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

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


        val rvForecastList : RecyclerView = view.findViewById(R.id.rv_forecastList)
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){
            showForecastDetails(it)
        }
        rvForecastList.adapter = dailyForecastAdapter
        rvForecastList.layoutManager = LinearLayoutManager(requireContext())




        val weeklyForecastObserver =  Observer<List<DailyForecast>> { forecastItems ->
            dailyForecastAdapter.submitList(forecastItems)
        }

        forecastRepository.weeklyForecast.observe(viewLifecycleOwner , weeklyForecastObserver)

        forecastRepository.loadForecast(zipcode)

        return view
    }

    private fun showLocationEntry(){
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun showForecastDetails(forecast: DailyForecast){
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment(forecast.temp,forecast.desc)
        findNavController().navigate(action)
    }

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
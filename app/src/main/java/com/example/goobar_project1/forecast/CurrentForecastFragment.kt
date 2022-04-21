package com.example.goobar_project1.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goobar_project1.*
import com.example.goobar_project1.details.ForecastDetailsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        //code here1

        val btnLocationEntry : FloatingActionButton = view.findViewById(R.id.btn_locationEntry)

        btnLocationEntry.setOnClickListener{
            appNavigator.navigateToLocationEntry()
        }

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val zipcode = requireArguments().getString(KEY_ZIPCODE) ?: ""


        val rvForecastList : RecyclerView = view.findViewById(R.id.rv_forecastList)
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){forecast ->
            forecastdetailsIntent(forecast)
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


    // Explicit Intent to share data b/w ForecastDetailsActivity
    private fun forecastdetailsIntent(forecast:DailyForecast){
        val forecastDetailsIntent = Intent(requireContext() , ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp",forecast.temp )
        forecastDetailsIntent.putExtra("key_desc",forecast.desc )
        startActivity(forecastDetailsIntent)
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
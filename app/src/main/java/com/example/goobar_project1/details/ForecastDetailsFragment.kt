package com.example.goobar_project1.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.goobar_project1.*

class ForecastDetailsFragment : Fragment() {

    private val args : ForecastDetailsFragmentArgs by navArgs()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forecast_details, container, false)

        //code here
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())



        val tempText = view.findViewById<TextView>(R.id.details_tvTemp)
        val descText = view.findViewById<TextView>(R.id.details_tvDesc)

        tempText.text =
            formatTempForDisplay(args.temp, tempDisplaySettingManager.getTempDisplaySettings())
        descText.text = args.description


        return view
    }
}
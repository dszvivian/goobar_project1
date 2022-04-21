package com.example.goobar_project1.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.goobar_project1.AppNavigator
import com.example.goobar_project1.R

class LocationEntryFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        // code here
        val etZipcode: EditText = view.findViewById(R.id.et_zipcode)
        val btnEnter: Button = view.findViewById(R.id.btn_zipcode)

        btnEnter.setOnClickListener {

            val zipcode: String = etZipcode.text.toString()

            if(zipcode.length != 5){
                Toast.makeText(requireContext(), R.string.error_zipcode_entry , Toast.LENGTH_SHORT).show()
                etZipcode.text.clear()
            }
            else{
//                forecastRepository.loadForecast(zipcode)
                appNavigator.navigateToCurrentForecast(zipcode)

            }

        }


        return view ;
    }


}
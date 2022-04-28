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
import androidx.navigation.fragment.findNavController
import com.example.goobar_project1.Location
import com.example.goobar_project1.LocationRepository
import com.example.goobar_project1.R

class LocationEntryFragment : Fragment() {

    private lateinit var locationRepository: LocationRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        locationRepository = LocationRepository(requireContext())

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
                locationRepository.saveLocation(Location.Zipcode(zipcode))
                findNavController().navigateUp()

            }

        }

        return view
    }


}
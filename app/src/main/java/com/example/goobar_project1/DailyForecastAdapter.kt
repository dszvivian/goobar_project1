package com.example.goobar_project1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

//region explanation

    //this is a Adapter for rvForecastList
    //we need two things -> Adapter and ViewHolder
    //ListAdapter is subclass of RecyclerView.Adapter

//endregion

class DailyForecastViewHolder(view : View ,
private val tempDisplaySettingManager: TempDisplaySettingManager
) : RecyclerView.ViewHolder(view){

    //declare the properties
    private val temptext = view.findViewById<TextView>(R.id.sample_tempText)
    private val descText = view.findViewById<TextView>(R.id.sample_descText)

    fun bind(dailyForecast: DailyForecast){
        // set the text
        temptext.text = formatTempForDisplay(dailyForecast.temp ,tempDisplaySettingManager.getTempDisplaySettings())
        descText.text = dailyForecast.desc.toString()
    }

}

class DailyForecastAdapter(
    private val tempDisplaySettingManager: TempDisplaySettingManager ,
    private val clickHandler : (DailyForecast) -> Unit
) : ListAdapter<DailyForecast,DailyForecastViewHolder>(DIFF_CONFIG) {

    companion object{

        val DIFF_CONFIG = object : DiffUtil.ItemCallback<DailyForecast>(){
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
                return oldItem == newItem
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        // creates new view to each item

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sample_itemdailyforecast , parent , false)
        return DailyForecastViewHolder(itemView , tempDisplaySettingManager)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        // brings each view : So that we can implement functionality to individual items

        holder.bind(getItem(position))

        holder.itemView.setOnClickListener{
            clickHandler(getItem(position))
        }

    }

}
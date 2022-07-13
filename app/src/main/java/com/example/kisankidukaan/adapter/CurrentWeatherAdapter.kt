package com.example.kisankidukaan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.kisankidukaan.R
import com.example.kisankidukaan.models.WeatherList
import kotlinx.android.synthetic.main.single_currentweather.view.*


class CurrentWeatherAdapter(var currentData: MutableList<WeatherList>) :
    RecyclerView.Adapter<CurrentWeatherAdapter.CurrentWeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentWeatherViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.single_currentweather,parent,false)
        return CurrentWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrentWeatherViewHolder, position: Int) {
        currentData[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return currentData.size
    }


    inner class CurrentWeatherViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(it: WeatherList) =with(itemView)  {
            tvTodayTitle.text = "Today, " + it.dt_txt.slice(10..15)

            val icon = it.weather[0].icon
            val iconURL = "https://openweathermap.org/img/w/$icon.png"
            Glide.with(itemView.context)
                .load(iconURL)
                .into(ivCurrentWeatherIcon)

            val temperature = it.main.temp - 273.15
            tvCurrentTemp.text = temperature.toInt().toString() + "\u2103"

            tvCurrentDesc.text = it.weather[0].description.uppercase()

            val minTemperature = it.main.temp_min - 273.15
            tvMinTemp.text = minTemperature.toInt().toString() + "\u2103"

            val maxTemperature = it.main.temp_max - 273.15
            tvMaxTemp.text = maxTemperature.toInt().toString() +  "\u2103"

            tvHumidity.text = it.main.humidity.toString() +"%"

        }

    }

}





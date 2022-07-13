package com.example.kisankidukaan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kisankidukaan.R
import com.example.kisankidukaan.models.WeatherList
import kotlinx.android.synthetic.main.single_weather_snippet.view.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherAdapter(var data: MutableList<WeatherList>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.single_weather_snippet,parent,false)
        return WeatherViewHolder(item)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {

        data[position].let { holder.bind(it) }

    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class WeatherViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        fun bind(it: WeatherList) = with(itemView) {

            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat = SimpleDateFormat("dd/MM/yyyy")


            val date: Date = inputFormat.parse(it.dt_txt.slice(0..9))!!
            val outputDate = outputFormat.format(date)

            tvWeatherDate.text = outputDate.toString()

            val icon = it.weather[0].icon
            val iconURL = "https://openweathermap.org/img/w/$icon.png"
            Glide.with(itemView.context)
                .load(iconURL)
                .into(weatherIcon)

            val temperature = it.main.temp - 273.15
            tvweatherTemperature.text = temperature.toInt().toString() + "\u2103"

            tvweatherDescription.text = it.weather[0].description.uppercase()

        }

    }




}
package com.example.kisankidukaan.screens.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kisankidukaan.R
import com.example.kisankidukaan.adapter.CurrentWeatherAdapter
import com.example.kisankidukaan.adapter.WeatherAdapter
import com.example.kisankidukaan.models.Weather
import com.example.kisankidukaan.models.WeatherList
import com.example.kisankidukaan.network.*
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var Adapter: WeatherAdapter
    lateinit var currentAdapter:CurrentWeatherAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val cityName = bundle!!.getString("City")
        val lati = bundle!!.getString("Latitude")
        val longi = bundle!!.getString("Longitude")
        weatherCity.text = cityName
        Toast.makeText(requireContext(),"${lati} ${longi}",Toast.LENGTH_LONG).show()

        rcylr_weather.visibility = View.GONE
        currentWeather_rcycl.visibility = View.GONE

        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Getting Data")
        progressDialog.setCancelable(false)
        progressDialog.show()

        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) {
                WeatherAPI.ResultRequest.GetWeather(lati!!, longi!!)

            }
            if (response.isSuccessful) {

                val weather: Weather? = response.body()


                if (progressDialog.isShowing) progressDialog.dismiss()

                rcylr_weather.visibility = View.VISIBLE
                currentWeather_rcycl.visibility = View.VISIBLE

                val currentData = getTodayData(weather!!)
                currentAdapter = CurrentWeatherAdapter(currentData)
                currentWeather_rcycl.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                currentWeather_rcycl.adapter =currentAdapter


                val fiveDayData = getFiveDaysData(weather)
                Adapter = WeatherAdapter(fiveDayData)
                rcylr_weather.layoutManager = LinearLayoutManager(requireContext())
                rcylr_weather.adapter = Adapter
            }else{
                Toast.makeText(requireContext(),"Error",Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun getTodayData(weather: Weather): MutableList<WeatherList> {
        val firstDate = weather.list[0].dt_txt.slice(8..9)
        var otherDates = firstDate
        var i=1;
        val data = mutableListOf<WeatherList>()

        while (otherDates == firstDate) {
            data.add(weather.list[i - 1])
            otherDates = weather.list[i].dt_txt.slice(8..9)
            i += 1
        }
        return data
    }


    private fun getFiveDaysData(weather: Weather): MutableList<WeatherList> {
        val data = mutableListOf<WeatherList>()
        for (i in 0..39) {
            if (weather.list[i].dt_txt.slice(11..12) == "12") {
                Log.d("Something date", weather.list[i].dt_txt)
                data.add(weather.list[i])
            }
        }
        return data
    }




}
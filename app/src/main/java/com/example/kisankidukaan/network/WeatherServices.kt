package com.example.kisankidukaan.network

import com.example.kisankidukaan.models.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServices {
    @GET("data/2.5/forecast?appid=63259e8886cbe4d575c24358fb860b1b")
    suspend fun GetWeather(@Query("lat")lat:String, @Query("lon")lon:String): Response<Weather>
}
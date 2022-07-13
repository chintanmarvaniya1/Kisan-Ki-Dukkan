package com.example.kisankidukaan.network


import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//https://api.openweathermap.org/data/2.5/forecast?lat=19.0&lon=72.87&appid=63259e8886cbe4d575c24358fb860b1b
object WeatherAPI {

    val Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()


    val RetrofitRequest = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create(Gson))
        .build()


    val ResultRequest = RetrofitRequest.create(WeatherServices::class.java)

}
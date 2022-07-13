package com.example.kisankidukaan.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APMC_API {
    val Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()


    val RetrofitRequest = Retrofit.Builder()
        .baseUrl("https://api.data.gov.in/")
        .addConverterFactory(GsonConverterFactory.create(Gson))
        .build()


    val ResultRequest = RetrofitRequest.create(APMC_Services::class.java)
}
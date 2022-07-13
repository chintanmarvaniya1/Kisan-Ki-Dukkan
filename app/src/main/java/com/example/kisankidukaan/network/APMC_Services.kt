package com.example.kisankidukaan.network

import com.example.kisankidukaan.models.APMC
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APMC_Services {

    @GET("resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001987c65666f9c49656f0f9ef4fa3650e7&format=json&offset=0&limit=4000")
    suspend fun getapmc(@Query("limit")limit:Int): Response<APMC>
    @GET("resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001194f3c2b5d41482e420d012d74a201fc&format=json&offset=0&limit=7000")
    suspend fun getSomeData(@Query("filters[district]")filter:String): Response<APMC>
}
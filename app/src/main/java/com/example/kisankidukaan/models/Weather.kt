package com.example.kisankidukaan.models

import com.google.gson.annotations.SerializedName

data class Weather(

    @SerializedName("cod")
    val cod: String,

    @SerializedName("message")
    val message: Int,

    @SerializedName("cnt")
    val cnt: Int,

    @SerializedName("list")
    val list: ArrayList<WeatherList>,

    @SerializedName("city")
    val city: City

)

data class WeatherList(

    @SerializedName("dt")
    val dt: Long,

    @SerializedName("main")
    val main: WeatherMain,

    @SerializedName("weather")
    val weather: List<WeatherSub>,

    @SerializedName("clouds")

    val clouds: Clouds,

    @SerializedName("wind")
    val wind: Wind,

    @SerializedName("visibility")
    val visibility: Int,

    @SerializedName("pop")
    val pop: Float,

    @SerializedName("sys")
    val sys: Sys,

    @SerializedName("dt_txt")
    val dt_txt: String
)

data class WeatherMain(
    @SerializedName("temp")
    val temp: Float,

    @SerializedName("feels_like")
    val feels_like: Float,

    @SerializedName("temp_min")
    val temp_min: Float,

    @SerializedName("temp_max")
    val temp_max: Float,

    @SerializedName("pressure")
    val pressure: Int,

    @SerializedName("sea_level")
    val sea_level: Int,

    @SerializedName("grnd_level")
    val grnd_level: Int,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("temp_kf")
    val temp_kf: Float

)

data class WeatherSub(
    @SerializedName("id")
    val id: Int,

    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String
)

data class Clouds(

    @SerializedName("all")
    val all:Int
)

data class Wind(
    @SerializedName("speed")
    val speed: Float,

    @SerializedName("deg")
    val deg: Float,

    @SerializedName("gust")
    val gust: Float
)

data class Sys(
    @SerializedName("pod")
    val pod :String
)

data class City (

    @SerializedName("id"         ) var id         : Int?    = null,
    @SerializedName("name"       ) var name       : String? = null,
    @SerializedName("coord"      )
    var coord      : Coord?  = Coord(),
    @SerializedName("country"    ) var country    : String? = null,
    @SerializedName("population" ) var population : Int?    = null,
    @SerializedName("timezone"   ) var timezone   : Int?    = null,
    @SerializedName("sunrise"    ) var sunrise    : Int?    = null,
    @SerializedName("sunset"     ) var sunset     : Int?    = null

)


data class Coord (

    @SerializedName("lat" ) var lat : Double?    = null,
    @SerializedName("lon" ) var lon : Double? = null

)
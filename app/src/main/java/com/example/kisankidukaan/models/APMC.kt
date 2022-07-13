package com.example.kisankidukaan.models

import com.google.gson.annotations.SerializedName

data class APMC(

    @SerializedName("created")
    val created : Int,

    @SerializedName("updated")
    val updated : Int,

    @SerializedName("created_date")
    val created_date : String,

    @SerializedName("updated_date")
    val updated_date : String,

    @SerializedName("active")
    val active:String,

    @SerializedName("index_name")
    val index_name : String,

    @SerializedName("org")
    val org : List<String>,

    @SerializedName("org_type")
    val org_type : String,

    @SerializedName("source")
    val source : String,

    @SerializedName("title")
    val title : String,

    @SerializedName("external_ws_url")
    val external_ws_url : String,

    @SerializedName("visualizable")
    val visualizable : String,

    @SerializedName("field")
    val field: List<SubField>,

    @SerializedName("external_ws")
    val external_ws   : Int,

    @SerializedName("catalog_uuid"  )
    val catalog_uuid  : String,

    @SerializedName("sector")
    val sector : List<String> ,

    @SerializedName("target_bucket" )
    val target_bucket : TargetBucket,

    @SerializedName("desc")
    val desc : String,

    @SerializedName("message")
    val message : String,

    @SerializedName("version")
    val version : String,

    @SerializedName("status")
    val status : String,

    @SerializedName("total")
    val total : Int,

    @SerializedName("count")
    val count : Int,

    @SerializedName("limit")
    val limit : String,

    @SerializedName("offset")
    val offset : String,

    @SerializedName("records")
    val records: List<APMCRecords>
)

data class TargetBucket(
    @SerializedName("field" )
    val field : String,

    @SerializedName("index" )
    val index : String,

    @SerializedName("type"  )
    val type  : String
)

data class SubField(
    @SerializedName("name")
    val name : String,

    @SerializedName("id")
    val id : String,

    @SerializedName("type")
    val type: String
)

data class APMCRecords(
    @SerializedName("state")
    val state:String,

    @SerializedName("district")
    val district:String,

    @SerializedName("market")
    val market:String,

    @SerializedName("commodity")
    val commodity:String,

    @SerializedName("variety")
    val variety:String,

    @SerializedName("arrival_date")
    val arrival_date:String,

    @SerializedName("min_price")
    val min_price:String,

    @SerializedName("max_price")
    val max_price:String,

    @SerializedName("modal_price")
    val modal_price:String
)
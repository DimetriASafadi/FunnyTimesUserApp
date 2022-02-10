package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTService(
    @SerializedName("id")
    var ServiceId:Int? = null,
    @SerializedName("name")
    var ServiceName:String? = "",
    @SerializedName("img")
    var ServiceImage:String? = "",
    var ServiceIsFavourite:Boolean? = false,
    @SerializedName("address")
    var ServiceLocation:String? = "",
    @SerializedName("star")
    var ServiceRate:Double? = 0.0,
    var ServiceReviews:Int? = null,
    @SerializedName("type")
    var ServiceType:String? = "",

):Serializable
package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTFilterCity(
    @SerializedName("id")
    val CityId:Int?,
    @SerializedName("name")
    val CityName:String?,
    @SerializedName("image")
    val CityImage:String?
):Serializable
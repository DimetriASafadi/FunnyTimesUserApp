package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTCategory(
    @SerializedName("id")
    var CategoryId:Int?,
    @SerializedName("name")
    var CategoryName:String?,
    @SerializedName("icon")
    var CategoryIcon:String?,
    @SerializedName("image")
    var CategoryImg:String?,
    @SerializedName("type")
    var CategoryType:String?
):Serializable
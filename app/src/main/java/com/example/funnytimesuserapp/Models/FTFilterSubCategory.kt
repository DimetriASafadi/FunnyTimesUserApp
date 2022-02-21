package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class FTFilterSubCategory(
    @SerializedName("id")
    val SubCatId:Int?,
    @SerializedName("name")
    val SubCatName:String?,
    @SerializedName("icon")
    val SubCatIcon:String?

):Serializable
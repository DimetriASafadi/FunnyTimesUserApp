package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTBanner(
    @SerializedName("id")
    var BannerId:Int? = null,
    @SerializedName("name")
    var BannerName:String? = "",
    @SerializedName("url")
    var BannerUrl:String? = "",
    @SerializedName("imgs")
    var BannerImage:String? = "",
):Serializable
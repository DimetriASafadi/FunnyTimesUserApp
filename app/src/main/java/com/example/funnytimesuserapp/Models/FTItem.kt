package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTItem(
    @SerializedName("id")
    var ItemId:Int? = null,
    @SerializedName("name")
    var ItemName:String? = "",
    @SerializedName("img")
    var ItemImage:String? = "",
    var ItemIsFavorite:Boolean,
    @SerializedName("address")
    var ItemLocation:String? = "",
    @SerializedName("star")
    var ItemRating:Double? = 0.0,
    var ItemRatingText:Int,
    @SerializedName("vendor_name")
    var ItemShop:String? = "",
    @SerializedName("price")
    var ItemPrice:String? = "0.0",
    @SerializedName("type")
    var ItemType:String? = ""


):Serializable
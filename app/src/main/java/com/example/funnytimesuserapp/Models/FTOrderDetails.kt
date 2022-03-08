package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTOrderDetails(
    @SerializedName("id")
    var ItemId:Int?,
    @SerializedName("name")
    var ItemName:String?,
    @SerializedName("star")
    var ItemRate:Int?,
    @SerializedName("type")
    var ItemType:String?,
    @SerializedName("vendor_name")
    var ItemVendorName:String?,
    @SerializedName("address")
    var ItemAddress:String?,
    @SerializedName("price")
    var ItemPrice:String?,
    @SerializedName("img")
    var ItemImage:String?,
):Serializable

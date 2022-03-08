package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTOrderItem(
    @SerializedName("data")
    var ItemDetails:FTOrderDetails,
    @SerializedName("qty")
    var ItemQuantity:Int?,
    @SerializedName("total")
    var ItemTotal:String?,
    @SerializedName("price")
    var ItemPrice:String?,
    @SerializedName("attributes")
    var ItemAttributes:List<FTOrderItemAttribute>?

):Serializable

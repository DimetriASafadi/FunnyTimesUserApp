package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTOrderItemAttribute(
    @SerializedName("name")
    var AttributeName:String,
    @SerializedName("value")
    var AttributeValue:String,
):Serializable

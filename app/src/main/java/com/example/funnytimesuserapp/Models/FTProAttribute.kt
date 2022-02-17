package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTProAttribute(
    @SerializedName("name")
    val AttrName:String? = "",
    @SerializedName("id")
    val AttrId:Int? = null,
    @SerializedName("attribute_id")
    val AttrTypeId:Int? = null
):Serializable

package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTBookDetails(

    @SerializedName("id")
    var BookPropId:Int?,
    @SerializedName("name")
    var BookPropName:String?,
    @SerializedName("star")
    var BookPropRate:String?,
    @SerializedName("type")
    var BookPropType:String?,
    @SerializedName("vendor_name")
    var BookPropVendor:String?,
    @SerializedName("address")
    var BookPropAddress:String?,
    @SerializedName("img")
    var BookPropImg:String?,
    @SerializedName("description")
    var BookPropDesc:String?,
    @SerializedName("user_id")
    var BookVendorId:Int?,
    @SerializedName("user_name")
    var BookVendorName:String?,
    @SerializedName("services")
    var BookPropServices:List<FTClinicService>?,

    ):Serializable

package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTBook(
    @SerializedName("id")
    var BookId:Int?,
    @SerializedName("name")
    var BookName:String,
    @SerializedName("start_date")
    var BookStartDate:String? = "",
    @SerializedName("end_date")
    var BookEndDate:String? = "",
    @SerializedName("start_hour")
    var BookStartHour:String? = "",
    @SerializedName("end_hour")
    var BookEndHour:String? = "",
    @SerializedName("period")
    var BookPeriod:Int?,
    @SerializedName("total")
    var BookTotal:Int?,
    @SerializedName("nights_count")
    var BookNightCount:Int?,
    @SerializedName("booking_type")
    var BookType:Int?,
    @SerializedName("status")
    var BookStatus:String?,
    @SerializedName("payment_gateway")
    var BookPayment:String?,
    @SerializedName("data")
    var BookDetails:FTBookDetails?,
    @SerializedName("created_at")
    var BookCreatedAt:String?,




    ):Serializable

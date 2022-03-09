package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTBook(
    @SerializedName("id")
    var BookId:Int? = 0,
    @SerializedName("name")
    var BookName:String? = "لا يوجد",
    @SerializedName("start_date")
    var BookStartDate:String? = "لا يوجد",
    @SerializedName("end_date")
    var BookEndDate:String? = "لا يوجد",
    @SerializedName("start_hour")
    var BookStartHour:String? = "لا يوجد",
    @SerializedName("end_hour")
    var BookEndHour:String? = "لا يوجد",
    @SerializedName("period")
    var BookPeriod:Int? = 0,
    @SerializedName("total")
    var BookTotal:Int? = 0,
    @SerializedName("nights_count")
    var BookNightCount:Int? = 0,
    @SerializedName("booking_type")
    var BookType:Int? = 0,
    @SerializedName("status")
    var BookStatus:String? = "لا يوجد",
    @SerializedName("payment_gateway")
    var BookPayment:String? = "لا يوجد",
    @SerializedName("data")
    var BookDetails:FTBookDetails?,
    @SerializedName("created_at")
    var BookCreatedAt:String? = "لا يوجد",




    ):Serializable

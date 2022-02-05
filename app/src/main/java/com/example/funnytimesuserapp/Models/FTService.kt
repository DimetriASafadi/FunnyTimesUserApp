package com.example.funnytimesuserapp.Models

data class FTService(
    var ServiceId:Int,
    var ServiceName:String,
    var ServiceImage:String,
    var ServiceIsFavourite:Boolean,
    var ServiceLocation:String,
    var ServiceRate:Double,
    var ServiceReviews:Int
)
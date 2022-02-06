package com.example.funnytimesuserapp.Models

import java.io.Serializable

data class FTCategory(
    var CategoryId:Int,
    var CategoryName:String,
    var CategoryIcon:Int,
    var CategoryLevel:Int
):Serializable
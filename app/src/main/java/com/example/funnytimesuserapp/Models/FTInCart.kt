package com.example.funnytimesuserapp.Models

data class FTInCart(
    var ItemId:Int,
    var ItemProId:String = "",
    var ItemName:String,
    var ItemImage:String,
    var ItemVendor:String,
    var ItemLocation:String,
    var ItemPrice:Double,
    var ItemRate:String,
    var ItemRateText:Int,
    var ItemQuantity:Int? = 1,
    var ItemAttributes:ArrayList<FTProAttribute> = ArrayList()
)

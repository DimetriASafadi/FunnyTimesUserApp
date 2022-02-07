package com.example.funnytimesuserapp.Models

import java.io.Serializable

data class FTItem(
    var ItemId:Int,
    var ItemName:String,
    var ItemImage:String,
    var ItemIsFavorite:Boolean,
    var ItemLocation:String,
    var ItemRating:Double,
    var ItemRatingText:Int,
    var ItemShop:String,
    var ItemPrice:Int,


):Serializable
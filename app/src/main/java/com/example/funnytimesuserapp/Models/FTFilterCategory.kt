package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTFilterCategory(
    @SerializedName("name")
    val CatName:String?,
    @SerializedName("id")
    val CatId:Int?,
    @SerializedName("sub_categorises")
    val CatSubCats:List<FTFilterSubCategory>
):Serializable
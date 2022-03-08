package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTOrder(
    @SerializedName("id")
    var OrderId:Int?,
    @SerializedName("total")
    var OrderTotal:String?,
    @SerializedName("payment_status")
    var OrderPaymentStatus:String?,
    @SerializedName("store_name")
    var OrderVendor:String?,
    @SerializedName("created_at")
    var OrderCreatedAt:String?,
    @SerializedName("status")
    var OrderStatus:String?,
    @SerializedName("payment_gateway")
    var OrderPayGateway:String?,
    @SerializedName("items")
    var OrderItems:List<FTOrderItem>?

):Serializable

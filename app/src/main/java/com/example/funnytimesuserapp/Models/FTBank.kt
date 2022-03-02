package com.example.funnytimesuserapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FTBank(
    @SerializedName("id")
    var BankId:Int,
    @SerializedName("name")
    var BankName:String,
    @SerializedName("IBAN")
    var BankIban:String,
    @SerializedName("number")
    var BankNumber:String,
    var BankSelected:Boolean = false

):Serializable
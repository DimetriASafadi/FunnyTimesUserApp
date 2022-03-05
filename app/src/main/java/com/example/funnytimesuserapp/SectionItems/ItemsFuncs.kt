package com.example.funnytimesuserapp.SectionItems

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.funnytimesuserapp.CommonSection.Constants.AppSPName
import com.example.funnytimesuserapp.Models.FTInCart
import com.example.funnytimesuserapp.Models.FTProAttrContainer
import com.example.funnytimesuserapp.Models.FTProAttribute
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ItemsFuncs {


    fun UpdateMyCart(context: Context, Myitems:ArrayList<FTInCart>){

        val sharedPreferences: SharedPreferences = context.getSharedPreferences(AppSPName,
            Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(Myitems)
        editor.putString("InCartItems", json)
        editor.apply()

    }

    fun AddToCart(context: Context,fTInCart:FTInCart){
        var customAreas = GetMyCart(context)
        customAreas.add(fTInCart)
        UpdateMyCart(context,customAreas)
    }

    fun DeleteFromCart(context: Context,index:Int){
        var customAreas = GetMyCart(context)
        customAreas.removeAt(index)
        UpdateMyCart(context,customAreas)
    }

    fun GetMyCart(context: Context):ArrayList<FTInCart>{
        var customAreas = ArrayList<FTInCart>()
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(AppSPName,
            Context.MODE_PRIVATE)
        val stringAreas = sharedPreferences.getString("InCartItems",null)
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<FTInCart?>?>() {}.type
        if (stringAreas != null){
            customAreas = gson.fromJson(stringAreas,type)
        }
        return customAreas
    }

    fun GetCartTotal(Myitems:ArrayList<FTInCart>):Double{
        var total = 0.0
        for (i in 0 until Myitems.size) {
            total += Myitems[i].ItemPrice * Myitems[i].ItemQuantity!!.toDouble()
        }
        return total
    }

    fun CheckProduct(itemId:Int,context: Context):Boolean{
        val Myitems = GetMyCart(context)
        for (i in 0 until Myitems.size) {
            if (Myitems[i].ItemId == itemId){
                return true
            }
        }
        return false
    }

    fun GetSelectedAttributesString(data:ArrayList<FTProAttrContainer>):String{
        var result = ""
        for (i in 0 until data.size) {
            for (a in 0 until data[i].ContainerAttributes.size) {
                if (data[i].ContainerAttributes[a].AttributeIsSelected){
                    result = result+"("+data[i].ContainerAttributes[a].AttrId+","+data[i].ContainerAttributes[a].AttrTypeId+")"
                }
            }
        }
        return result
    }
    fun CheckProductAttributes(itemId:String,context: Context,selectedAtrr:String):Boolean{
        val Myitems = GetMyCart(context)
        if (Myitems.size != 0){
            for (i in 0 until Myitems.size) {
                Log.e(Myitems[i].ItemProId," == "+itemId)
                if (Myitems[i].ItemProId == itemId){
                    for (q in 0 until Myitems[i].ItemAttributes.size) {
                        val selfatt = "("+Myitems[i].ItemAttributes[q].AttrId+","+Myitems[i].ItemAttributes[q].AttrTypeId+")"
                        if (!selectedAtrr.contains(selfatt)){
                            Log.e("return","false1")
                            return false
                        }
                    }
                    return true
                }
            }
            return false
        }else{
            Log.e("return","false2")
            return false
        }

    }

    fun GetSelectedAttributes(data:ArrayList<FTProAttrContainer>):ArrayList<FTProAttribute>{
        val result = ArrayList<FTProAttribute>()
        for (i in 0 until data.size) {
            for (a in 0 until data[i].ContainerAttributes.size) {
                if (data[i].ContainerAttributes[a].AttributeIsSelected){
                    data[i].ContainerAttributes[a].AttrName = data[i].ContainerName+":"+data[i].ContainerAttributes[a].AttrName
                    result.add(data[i].ContainerAttributes[a])
                }
            }
        }
        return result
    }

    fun GetProIdString(itemId: Int,data:ArrayList<FTProAttrContainer>):String{
        var result = itemId.toString()
        for (i in 0 until data.size) {
            for (a in 0 until data[i].ContainerAttributes.size) {
                if (data[i].ContainerAttributes[a].AttributeIsSelected){
                    result = result+"("+data[i].ContainerAttributes[a].AttrId+","+data[i].ContainerAttributes[a].AttrTypeId+")"
                }
            }
        }
        return result
    }

    fun GetProAttributesNames(attrbs:ArrayList<FTProAttribute>):String{
        var result = ""
        for (i in 0 until attrbs.size) {
            result = " "+result+attrbs[i].AttrName+" "
        }
        return result
    }

    fun CheckItems(items:ArrayList<FTInCart>):Boolean{
        for (i in 0 until items.size) {
            if (items[0].ItemVendorId != items[i].ItemVendorId){
                return true
            }
        }
        return false
    }



}
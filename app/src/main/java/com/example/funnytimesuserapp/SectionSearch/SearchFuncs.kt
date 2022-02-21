package com.example.funnytimesuserapp.SectionSearch

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants.FilterTools
import com.example.funnytimesuserapp.Models.FTFilterCategory
import com.example.funnytimesuserapp.Models.FTFilterCity
import com.example.funnytimesuserapp.Models.FTFilterSubCategory
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.SpinnerAdapters.SFilterCategoriesAdapter
import com.example.funnytimesuserapp.SpinnerAdapters.SFilterCitiesAdapter
import com.example.funnytimesuserapp.SpinnerAdapters.SFilterSubCategoryAdapter
import com.google.gson.GsonBuilder
import com.jaygoo.widget.RangeSeekBar
import org.json.JSONObject

class SearchFuncs {
    val commonFuncs = CommonFuncs()
    var filterDialog: Dialog? = null


    fun showFilterDialog(activity: Activity) {
        var city_id = ""
        var address = ""
        var name = ""
        var pricefrom = ""
        var priceto = ""
        var category_id = ""
        var sub_category_id = ""

        filterDialog = Dialog(activity)
        filterDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        filterDialog?.setCancelable(true)
        filterDialog?.setContentView(R.layout.ft_dialog_main_filter)
        val filterCities = ArrayList<FTFilterCity>()
        val filterCategories = ArrayList<FTFilterCategory>()
        val filterSubCategory = ArrayList<FTFilterSubCategory>()
        val filterTools = commonFuncs.GetFromSP(activity,FilterTools)
        if (filterTools != "NoValue"){
            val data = JSONObject(filterTools)
        val gson = GsonBuilder().create()
            filterCities.addAll(gson.fromJson(data.getJSONArray("cites").toString(),Array<FTFilterCity>::class.java).toList())
            filterCategories.addAll(gson.fromJson(data.getJSONArray("categories").toString(),Array<FTFilterCategory>::class.java).toList())
            val sFilterCitiesAdapter = SFilterCitiesAdapter(activity,filterCities)
            val sFilterCategoriesAdapter = SFilterCategoriesAdapter(activity,filterCategories)
            val sFilterSubCategoryAdapter = SFilterSubCategoryAdapter(activity,filterSubCategory)
            val FilterCitySpinner = filterDialog?.findViewById<Spinner>(R.id.FilterCitySpinner)
            val FilterCategorySpinner = filterDialog?.findViewById<Spinner>(R.id.FilterCategorySpinner)
            val FilterSubCategorySpinner = filterDialog?.findViewById<Spinner>(R.id.FilterSubCategorySpinner)
                FilterCitySpinner!!.adapter = sFilterCitiesAdapter
                FilterCategorySpinner!!.adapter = sFilterCategoriesAdapter
                FilterSubCategorySpinner!!.adapter = sFilterSubCategoryAdapter
            val FilterDistricName = filterDialog?.findViewById<EditText>(R.id.FilterDistricName)
            val FilterPlaceName = filterDialog?.findViewById<EditText>(R.id.FilterPlaceName)
            val FilterResults = filterDialog?.findViewById<TextView>(R.id.FilterResults)
            val FilterClear = filterDialog?.findViewById<TextView>(R.id.FilterClear)
            val rangeseek = filterDialog?.findViewById<RangeSeekBar>(R.id.PriceRangeBar)
            val BarMax = filterDialog?.findViewById<TextView>(R.id.BarMax)
            val BarMin = filterDialog?.findViewById<TextView>(R.id.BarMin)
        rangeseek?.setOnRangeChangedListener { view, min, max, isFromUser ->
            BarMax?.text = max.toInt().toString()+" ر.س"
            BarMin?.text = min.toInt().toString()+" ر.س"
            pricefrom = min.toInt().toString()
            priceto = max.toInt().toString()
        }
            FilterCitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    city_id = sFilterCitiesAdapter.getItem(position)!!.CityId.toString()
                }
            }
            FilterCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    filterSubCategory.clear()
                    filterSubCategory.addAll(sFilterCategoriesAdapter.getItem(position)!!.CatSubCats)
                    sFilterSubCategoryAdapter.notifyDataSetChanged()
                    FilterSubCategorySpinner.setSelection(0)
                    category_id = sFilterCategoriesAdapter.getItem(position)!!.CatId.toString()
                }
            }
            FilterSubCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    sub_category_id = sFilterSubCategoryAdapter.getItem(position)!!.SubCatId.toString()
                }
            }



            FilterResults!!.setOnClickListener {
                address = FilterDistricName!!.text.toString()
                name = FilterPlaceName!!.text.toString()
                sub_category_id = sFilterSubCategoryAdapter.getItem(FilterSubCategorySpinner.selectedItemPosition)!!.SubCatId.toString()
                if (filterDialog!!.isShowing){
                    filterDialog!!.dismiss()
                }
                val intent = Intent(activity,SearchScreen::class.java)
                intent.putExtra("city_id",city_id)
                intent.putExtra("address",address)
                intent.putExtra("name",name)
                intent.putExtra("price[from]",pricefrom)
                intent.putExtra("price[to]",priceto)
                intent.putExtra("category_id",category_id)
                intent.putExtra("sub_category_id",sub_category_id)
                activity.startActivity(intent)
            }
            FilterClear!!.setOnClickListener {
                FilterDistricName!!.setText("")
                FilterPlaceName!!.setText("")
                FilterCitySpinner.setSelection(0)
                FilterCategorySpinner.setSelection(0)
                FilterSubCategorySpinner.setSelection(0)
                rangeseek!!.setValue(0f,0f)
            }

        val window: Window = filterDialog?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(activity.resources
                .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        filterDialog?.show()
        }
    }

}
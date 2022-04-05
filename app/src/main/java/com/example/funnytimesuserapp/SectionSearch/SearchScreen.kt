package com.example.funnytimesuserapp.SectionSearch

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Models.FTFilterCategory
import com.example.funnytimesuserapp.Models.FTFilterCity
import com.example.funnytimesuserapp.Models.FTFilterSubCategory
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.ItemInsider2RecView
import com.example.funnytimesuserapp.SpinnerAdapters.SFilterCategoriesAdapter
import com.example.funnytimesuserapp.SpinnerAdapters.SFilterCitiesAdapter
import com.example.funnytimesuserapp.SpinnerAdapters.SFilterSubCategoryAdapter
import com.example.funnytimesuserapp.databinding.FtScreenSearchBinding
import com.google.gson.GsonBuilder
import com.jaygoo.widget.RangeSeekBar
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class SearchScreen : AppCompatActivity() {

    lateinit var binding: FtScreenSearchBinding

    val commonFuncs = CommonFuncs()
    var filterDialog: Dialog? = null

    val ftresult = ArrayList<FTItem>()
    lateinit var itemInsider2RecView : ItemInsider2RecView

    var city_id = ""
    var address = ""
    var name = ""
    var pricefrom = ""
    var priceto = ""
    var category_id = ""
    var sub_category_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenSearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        itemInsider2RecView = ItemInsider2RecView(ftresult,this)
        binding.ResultItemsRecycler.layoutManager = GridLayoutManager(this,2)
        binding.ResultItemsRecycler.adapter = itemInsider2RecView

        city_id = intent.getStringExtra("city_id").toString()
        address = intent.getStringExtra("address").toString()
        name = intent.getStringExtra("name").toString()
        pricefrom = intent.getStringExtra("price[from]").toString()
        priceto = intent.getStringExtra("price[to]").toString()
        category_id = intent.getStringExtra("category_id").toString()
        sub_category_id = intent.getStringExtra("sub_category_id").toString()

        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.SearchFilter.setOnClickListener {
            if (!filterDialog!!.isShowing){
                filterDialog!!.show()
            }
        }

        binding.SearchBar.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                filterSearch_Request(true)
                return@OnEditorActionListener true
            }
            false
        })
        filterSearch_Request(false)
        SetUpFilterDialog()


    }
    fun filterSearch_Request(isBar:Boolean){
        commonFuncs.showLoadingDialog(this)

        var url = Constants.APIMain + "api/filter/?"
        if (isBar){
            name = binding.SearchBar.text.toString()
            url = url+"&name=$name"
        }else{
            if (!city_id.isNullOrEmpty()){
                url = url+"&city_id=$city_id"
            }
            if (!address.isNullOrEmpty()){
                url = url+"&address=$address"
            }
            if (!name.isNullOrEmpty()){
                url = url+"&name=$name"
            }
            if (!pricefrom.isNullOrEmpty()){
                url = url+"&price[from]=$pricefrom"
            }
            if (!priceto.isNullOrEmpty()){
                url = url+"&price[to]=$priceto"
            }
            if (!category_id.isNullOrEmpty()){
                url = url+"&category_id=$category_id"
            }
            if (!category_id.isNullOrEmpty()){
                url = url+"&sub_category_id=$sub_category_id"
            }
        }
        Log.e("SearchUrl",url)
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONObject("data")
                    val products = data.getJSONArray("products")
                    val gson = GsonBuilder().create()
                    ftresult.clear()
                    ftresult.addAll(gson.fromJson(products.toString(),Array<FTItem>::class.java).toList())
                    itemInsider2RecView.notifyDataSetChanged()
                    commonFuncs.hideLoadingDialog()
                }, Response.ErrorListener { error ->
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(this,"خطأ في الاتصال",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(this,"خطأ في الاتصال","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()
                }) {
            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }

    fun SetUpFilterDialog() {
        filterDialog = Dialog(this)
        filterDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        filterDialog?.setCancelable(true)
        filterDialog?.setContentView(R.layout.ft_dialog_main_filter)
        val filterCities = ArrayList<FTFilterCity>()
        val filterCategories = ArrayList<FTFilterCategory>()
        val filterSubCategory = ArrayList<FTFilterSubCategory>()
        val filterTools = commonFuncs.GetFromSP(this, Constants.FilterTools)
        if (filterTools != "NoValue"){
            val data = JSONObject(filterTools)
            val gson = GsonBuilder().create()
            filterCities.addAll(gson.fromJson(data.getJSONArray("cites").toString(),Array<FTFilterCity>::class.java).toList())
            filterCategories.addAll(gson.fromJson(data.getJSONArray("categories").toString(),Array<FTFilterCategory>::class.java).toList())
            val sFilterCitiesAdapter = SFilterCitiesAdapter(this,filterCities)
            val sFilterCategoriesAdapter = SFilterCategoriesAdapter(this,filterCategories)
            val sFilterSubCategoryAdapter = SFilterSubCategoryAdapter(this,filterSubCategory)
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
                filterSearch_Request(false)

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
                ColorDrawable(this.resources
                    .getColor(R.color.tk_dialog_bg, null))
            )
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (filterDialog != null){
            if (filterDialog!!.isShowing){
                filterDialog!!.dismiss()
            }
        }
    }
}
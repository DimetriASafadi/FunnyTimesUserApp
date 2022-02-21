package com.example.funnytimesuserapp.SectionCategories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Interfaces.SubCategoryClickListener
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTSubCategory
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.ServiceFullHorizontalRecView
import com.example.funnytimesuserapp.RecViews.ServiceInsider2RecView
import com.example.funnytimesuserapp.RecViews.SubCategoriesRecView
import com.example.funnytimesuserapp.SectionSearch.SearchFuncs
import com.example.funnytimesuserapp.SectionSearch.SearchScreen
import com.example.funnytimesuserapp.databinding.FtCategorySubServiceBinding
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class SubCategoryService : AppCompatActivity(), SubCategoryClickListener {

    lateinit var binding: FtCategorySubServiceBinding
    val suncategories = ArrayList<FTSubCategory>()
    val servicesarr = ArrayList<FTItem>()
    lateinit var serviceFullHorizontalRecView : ServiceFullHorizontalRecView
    lateinit var serviceInsiderRecView : ServiceInsider2RecView
    val commonFuncs = CommonFuncs()
    val searchFuncs = SearchFuncs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCategorySubServiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backBtn.setOnClickListener {
            finish()
        }

        val selfCat = intent.getSerializableExtra("subCat") as FTCategory
        val catPos = intent.getIntExtra("subPos",0)
        val selfCats = intent.getSerializableExtra("subCats") as ArrayList<FTCategory>
        for (i in 0 until selfCats.size)
        {
            suncategories.add(FTSubCategory(selfCats[i].CategoryId,selfCats[i].CategoryName,selfCats[i].CategoryIcon))
        }


        binding.SerSubCategoryName.text = selfCat.CategoryName



        val subCategoriesRecView = SubCategoriesRecView(suncategories,this,this,catPos)
        binding.SerCategoryRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.SerCategoryRecycler.adapter = subCategoriesRecView






        serviceFullHorizontalRecView = ServiceFullHorizontalRecView(servicesarr,this)
        serviceInsiderRecView = ServiceInsider2RecView(servicesarr,this)
        binding.ServicesRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)
        binding.ServicesRecycler.adapter = serviceFullHorizontalRecView

        var viewmode = 0

        binding.ShowingLargeSmall.setOnClickListener {
            if (viewmode == 1){
                viewmode = 0
                binding.ShowingLargeSmall.setImageResource(R.drawable.ft_dual_duo_view_icon)
                binding.ServicesRecycler.layoutManager = LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL,
                    false)
                binding.ServicesRecycler.adapter = serviceFullHorizontalRecView
            }else{
                viewmode = 1
                binding.ShowingLargeSmall.setImageResource(R.drawable.ft_vertical_view_icon)
                binding.ServicesRecycler.layoutManager = GridLayoutManager(this,2)
                binding.ServicesRecycler.adapter = serviceInsiderRecView
            }
        }
        binding.SearchFilter.setOnClickListener {
            searchFuncs.showFilterDialog(this)
        }
        binding.CategorySearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val intent = Intent(this, SearchScreen::class.java)
                intent.putExtra("city_id","")
                intent.putExtra("address","")
                intent.putExtra("name",binding.CategorySearch.text.toString())
                intent.putExtra("price[from]","")
                intent.putExtra("price[to]","")
                intent.putExtra("category_id","")
                intent.putExtra("sub_category_id","")
                startActivity(intent)
                return@OnEditorActionListener true
            }
            false
        })
        subcategory_Request(selfCat.CategoryId!!)

    }
    fun subcategory_Request(subcatid:Int){
        commonFuncs.showLoadingDialog(this)
        val url = Constants.APIMain + "api/subcategory/"+subcatid
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONObject("data")
                    val items = data.getJSONArray("items")

                    servicesarr.clear()
                    val gson = GsonBuilder().create()
                    servicesarr.addAll(gson.fromJson(items.toString(),Array<FTItem>::class.java).toList())

                    serviceFullHorizontalRecView.notifyDataSetChanged()
                    serviceInsiderRecView.notifyDataSetChanged()

                    commonFuncs.hideLoadingDialog()

                }, Response.ErrorListener { error ->
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(this,"خطأ في الإتصال",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(this,"خطأ في الإتصال","حصل خطأ ما")
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
    override fun OnSubCategoryClickListener(subcategory: FTSubCategory) {
        subcategory_Request(subcategory.SubCatId!!)
    }
}
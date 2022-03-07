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
import com.example.funnytimesuserapp.Interfaces.OnSubCategoryClick
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTSubCategory
import com.example.funnytimesuserapp.RecViews.ItemInsider2RecView
import com.example.funnytimesuserapp.RecViews.SubCategoriesRecView
import com.example.funnytimesuserapp.SectionSearch.SearchFuncs
import com.example.funnytimesuserapp.SectionSearch.SearchScreen
import com.example.funnytimesuserapp.databinding.FtCategorySubItemBinding
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class SubCategoryItem : AppCompatActivity() ,OnSubCategoryClick{

    lateinit var binding: FtCategorySubItemBinding
    val suncategories = ArrayList<FTSubCategory>()
    val itemssarr = ArrayList<FTItem>()
    val commonFuncs = CommonFuncs()
    val searchFuncs = SearchFuncs()


    lateinit var itemInsider2RecView : ItemInsider2RecView

    lateinit var selfCat:FTCategory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCategorySubItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backBtn.setOnClickListener {
            finish()
        }

        selfCat = intent.getSerializableExtra("subCat") as FTCategory
        val catPos = intent.getIntExtra("subPos",0)
        val selfCats = intent.getSerializableExtra("subCats") as ArrayList<FTCategory>
        for (i in 0 until selfCats.size)
        {
            suncategories.add(FTSubCategory(selfCats[i].CategoryId,selfCats[i].CategoryName,selfCats[i].CategoryIcon))
        }






        val subCategoriesRecView = SubCategoriesRecView(suncategories,this,this,catPos)
        binding.ItemCategoryRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.ItemCategoryRecycler.adapter = subCategoriesRecView



        itemInsider2RecView = ItemInsider2RecView(itemssarr,this)
        binding.ItemsSubCatRecycler.layoutManager = GridLayoutManager(this,2)
        binding.ItemsSubCatRecycler.adapter = itemInsider2RecView

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


    }

    override fun onResume() {
        super.onResume()
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

                    itemssarr.clear()
                    val gson = GsonBuilder().create()
                    itemssarr.addAll(gson.fromJson(items.toString(),Array<FTItem>::class.java).toList())
                    itemInsider2RecView.notifyDataSetChanged()



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
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(this@SubCategoryItem, Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(this@SubCategoryItem, Constants.KeyUserToken)
                        Log.e("HomeToken",commonFuncs.GetFromSP(this@SubCategoryItem, Constants.KeyUserToken).toString())
                    }
                    return map
                }
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
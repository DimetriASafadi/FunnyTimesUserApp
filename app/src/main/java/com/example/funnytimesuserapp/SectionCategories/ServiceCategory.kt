package com.example.funnytimesuserapp.SectionCategories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Interfaces.OnFavoriteClick
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTService
import com.example.funnytimesuserapp.Models.FTSubCategory
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.CategoriesRecView
import com.example.funnytimesuserapp.RecViews.ServiceInsiderRecView
import com.example.funnytimesuserapp.RecViews.ServicesBigVerticalRecView
import com.example.funnytimesuserapp.RecViews.SubCategoriesRecView
import com.example.funnytimesuserapp.SectionSearch.SearchFuncs
import com.example.funnytimesuserapp.SectionSearch.SearchScreen
import com.example.funnytimesuserapp.databinding.FtCategoryServiceBinding
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class ServiceCategory : AppCompatActivity(), OnFavoriteClick {

    lateinit var binding: FtCategoryServiceBinding
    val ftCategory = ArrayList<FTCategory>()
    val mostView = ArrayList<FTItem>()
    val mostClose = ArrayList<FTItem>()
    val Recent = ArrayList<FTItem>()

    val commonFuncs = CommonFuncs()
    val searchFuncs = SearchFuncs()

    lateinit var categoriesRecView: CategoriesRecView
    lateinit var servicesBigVerticalRecView : ServicesBigVerticalRecView
    lateinit var serviceInsiderRecView : ServiceInsiderRecView
    lateinit var serviceInsiderRecView2 : ServiceInsiderRecView

    var catId = 0
    var cattype = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCategoryServiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val selfCat = intent.getSerializableExtra("CategoryObj") as FTCategory
        catId = selfCat.CategoryId!!
        cattype = selfCat.CategoryType!!
        binding.SerCategoryName.text = selfCat.CategoryName

        binding.backBtn.setOnClickListener {
            finish()
        }

        categoriesRecView = CategoriesRecView(ftCategory,this,cattype,1)
        servicesBigVerticalRecView = ServicesBigVerticalRecView(mostView,this,this)
        serviceInsiderRecView = ServiceInsiderRecView(mostClose,this,this)
        serviceInsiderRecView2 = ServiceInsiderRecView(Recent,this,this)






        binding.SerCategoryRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.SerCategoryRecycler.adapter = categoriesRecView

        binding.MostVisited.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostVisited.adapter = servicesBigVerticalRecView

        binding.MostNearby.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostNearby.adapter = serviceInsiderRecView

        binding.MostRecent.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostRecent.adapter = serviceInsiderRecView2


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
        category_Request()
    }


    fun category_Request(){
        commonFuncs.showLoadingDialog(this)
        val url = Constants.APIMain + "api/category/"+catId
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONObject("data")
                    val categories = data.getJSONArray("subCategory")
                    val mostBooking = data.getJSONArray("mostView")
                    val mostOrder = data.getJSONArray("mostClose")
                    val mostShopping = data.getJSONArray("Recent")


                    val gson = GsonBuilder().create()
                    ftCategory.clear()
                    mostView.clear()
                    mostClose.clear()
                    Recent.clear()
                    ftCategory.add(FTCategory(0,"الكل","","",""))
                    ftCategory.addAll(gson.fromJson(categories.toString(),Array<FTCategory>::class.java).toList())
                    mostView.addAll(gson.fromJson(mostBooking.toString(),Array<FTItem>::class.java).toList())
                    mostClose.addAll(gson.fromJson(mostOrder.toString(),Array<FTItem>::class.java).toList())
                    Recent.addAll(gson.fromJson(mostShopping.toString(),Array<FTItem>::class.java).toList())

                    categoriesRecView.notifyDataSetChanged()
                    servicesBigVerticalRecView.notifyDataSetChanged()
                    serviceInsiderRecView.notifyDataSetChanged()
                    serviceInsiderRecView2.notifyDataSetChanged()


                    commonFuncs.hideLoadingDialog()

                }, Response.ErrorListener { error ->
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(this,"فشل تسجيل الدخول",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(this,"فشل تسجيل الدخول","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()

                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(this@ServiceCategory, Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(this@ServiceCategory, Constants.KeyUserToken)
                        Log.e("HomeToken",commonFuncs.GetFromSP(this@ServiceCategory, Constants.KeyUserToken).toString())
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

    override fun OnFavoriteClickListener(item: FTItem) {
        for (i in 0 until mostView.size) {
            if (mostView[i].ItemId == item.ItemId){
                mostView[i].ItemIsFavorite = item.ItemIsFavorite
            }
        }
        for (i in 0 until mostClose.size) {
            if (mostClose[i].ItemId == item.ItemId){
                mostClose[i].ItemIsFavorite = item.ItemIsFavorite
            }
        }

        for (i in 0 until Recent.size) {
            if (Recent[i].ItemId == item.ItemId){
                Recent[i].ItemIsFavorite = item.ItemIsFavorite
            }
        }
        servicesBigVerticalRecView.notifyDataSetChanged()
        serviceInsiderRecView.notifyDataSetChanged()
        serviceInsiderRecView2.notifyDataSetChanged()
    }

}
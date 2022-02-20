package com.example.funnytimesuserapp.SectionCategories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Models.FTBanner
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTSubCategory
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.*
import com.example.funnytimesuserapp.databinding.FtCategoryItemBinding
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class ItemCategory : AppCompatActivity() {

    lateinit var binding: FtCategoryItemBinding
    val ftCategory = ArrayList<FTCategory>()
    val MostOrder = ArrayList<FTItem>()
    val MostFamouse = ArrayList<FTItem>()
    val Recent = ArrayList<FTItem>()

    lateinit var categoriesRecView: CategoriesRecView
    lateinit var itemBigVerticalRecView : ItemBigVerticalRecView
    lateinit var itemInsiderRecView : ItemInsiderRecView
    lateinit var itemInsiderRecView2 : ItemInsiderRecView




    val commonFuncs = CommonFuncs()
    var catId = 0
    var cattype = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCategoryItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val selfCat = intent.getSerializableExtra("CategoryObj") as FTCategory
        catId = selfCat.CategoryId!!
        cattype = selfCat.CategoryType!!
        binding.ItemCategoryName.text = selfCat.CategoryName
        binding.backBtn.setOnClickListener {
            finish()
        }
        categoriesRecView = CategoriesRecView(ftCategory,this,cattype,1)
        itemBigVerticalRecView = ItemBigVerticalRecView(MostOrder,this)
        itemInsiderRecView = ItemInsiderRecView(MostFamouse,this)
        itemInsiderRecView2 = ItemInsiderRecView(Recent,this)





        binding.ItmCategoryRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.ItmCategoryRecycler.adapter = categoriesRecView

        binding.MostDemanded.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostDemanded.adapter = itemBigVerticalRecView

        binding.MostCommon.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostCommon.adapter = itemInsiderRecView

        binding.MostRecent.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostRecent.adapter = itemInsiderRecView2


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
                    val mostBooking = data.getJSONArray("MostOrder")
                    val mostOrder = data.getJSONArray("MostFamouse")
                    val mostShopping = data.getJSONArray("Recent")


                    val gson = GsonBuilder().create()
                    ftCategory.add(FTCategory(0,"الكل","","",""))
                    ftCategory.addAll(gson.fromJson(categories.toString(),Array<FTCategory>::class.java).toList())
                    MostOrder.addAll(gson.fromJson(mostBooking.toString(),Array<FTItem>::class.java).toList())
                    MostFamouse.addAll(gson.fromJson(mostOrder.toString(),Array<FTItem>::class.java).toList())
                    Recent.addAll(gson.fromJson(mostShopping.toString(),Array<FTItem>::class.java).toList())

                    categoriesRecView.notifyDataSetChanged()
                    itemBigVerticalRecView.notifyDataSetChanged()
                    itemInsiderRecView.notifyDataSetChanged()
                    itemInsiderRecView2.notifyDataSetChanged()


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

}
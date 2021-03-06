package com.example.funnytimesuserapp.SectionItems

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.MainMenuSection.FavouriteSection.FavoriteFuncs
import com.example.funnytimesuserapp.Models.FTInCart
import com.example.funnytimesuserapp.Models.FTItemPhoto
import com.example.funnytimesuserapp.Models.FTProAttribute
import com.example.funnytimesuserapp.Models.FTReview
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.ItemGalleryRecView
import com.example.funnytimesuserapp.RecViews.ReviewRecView
import com.example.funnytimesuserapp.SectionVendor.VendorScreen
import com.example.funnytimesuserapp.databinding.FtScreenFoodBinding
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class FoodScreen : AppCompatActivity() {

    lateinit var binding: FtScreenFoodBinding
    val commonFuncs = CommonFuncs()
    val favouriteFuncs = FavoriteFuncs()
    val itemFuncs = ItemsFuncs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenFoodBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val itemid = intent.getIntExtra("ItemId",0)
        Log.e("ProId",itemid.toString())

        binding.FoodBack.setOnClickListener {
            finish()
        }


        item_Request(this,itemid)
    }
    fun item_Request(activity: Activity,itemid :Int){
        commonFuncs.showLoadingDialog(activity)
        val url = Constants.APIMain + "api/product/"+itemid
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONObject("data")
                    val vendor = data.getJSONObject("vendor")

                    binding.FoodName.text = data.getString("name").toString()
                    var is_favourite = data.getBoolean("is_favourite")
                    if (is_favourite){
                        binding.FoodFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                    }else{
                        binding.FoodFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
                    }
                    binding.FoodFavorite.setOnClickListener {
                        if (commonFuncs.IsInSP(this, Constants.KeyUserToken)){
                            is_favourite = !is_favourite
                            if (is_favourite){
                                binding.FoodFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                                favouriteFuncs.add_favourite_Request(this,itemid)
                            }else{
                                binding.FoodFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
                                favouriteFuncs.delete_favourite_Request(this,itemid)
                            }
                        }else{
                            commonFuncs.showLoginDialog(this)
                        }
                    }
                    Glide.with(this)
                        .load(data.getString("img").toString())
                        .centerCrop()
                        .placeholder(R.drawable.ft_broken_image)
                        .into(binding.FoodImage)
                    Glide.with(this)
                        .load(vendor.getString("img").toString())
                        .centerCrop()
                        .placeholder(R.drawable.ft_broken_image)
                        .into(binding.VendorImage)
                    binding.FoodCity.text = data.getString("address")
                    binding.FoodVendorName.text = vendor.getString("name")
                    val vendortype = data.getString("type")
                    val vendorid = vendor.getInt("id")
                    binding.FoodVendorName.setOnClickListener {
                        val intent = Intent(this,VendorScreen::class.java)
                        intent.putExtra("vendorid",vendorid)
                        intent.putExtra("vendortype",vendortype)
                        startActivity(intent)
                    }
                    binding.VendorImage.setOnClickListener {
                        val intent = Intent(this,VendorScreen::class.java)
                        intent.putExtra("vendorid",vendorid)
                        intent.putExtra("vendortype",vendortype)
                        startActivity(intent)
                    }

                    binding.FoodDesc.text = data.getString("description")
                    binding.FoodRating.rating = data.getString("star").toFloat()
                    binding.FoodRating.setOnTouchListener { _, _ ->
                        return@setOnTouchListener true
                    }
                    binding.FoodPrice.text = data.getString("price")+" ??.??"
                    val gson = GsonBuilder().create()
                    val ftPhotos = ArrayList<FTItemPhoto>()
                    val ftReviews = ArrayList<FTReview>()
                    ftPhotos.addAll(gson.fromJson(data.getJSONArray("gallery").toString(),Array<FTItemPhoto>::class.java).toList())
                    ftReviews.addAll(gson.fromJson(data.getJSONArray("reviews").toString(),Array<FTReview>::class.java).toList())

                    val itemGalleryRecView = ItemGalleryRecView(ftPhotos,this)
                    binding.FoodGalleryRecycler.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL,
                        false)
                    binding.FoodGalleryRecycler.adapter = itemGalleryRecView
                    val reviewRecView = ReviewRecView(ftReviews,this)
                    binding.FoodReviewsRecycler.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false)
                    binding.FoodReviewsRecycler.adapter = reviewRecView




                    var isinCart = itemFuncs.CheckProduct(itemid,this)
                    if (isinCart){
                        binding.InCartNow.text = "?????????? ???? ????????????"
                        binding.InCartNow.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_orange,null))
                    }else{
                        binding.InCartNow.setOnClickListener {
                            if (isinCart){
                                binding.InCartNow.text = "?????????? ???? ????????????"
                                binding.InCartNow.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_orange,null))
                            }else{
                                itemFuncs.AddToCart(this,
                                    FTInCart(
                                        itemid,
                                        "",
                                        data.getString("name").toString(),
                                        data.getString("img").toString(),
                                        vendor.getString("name"),
                                        vendor.getInt("id"),
                                        data.getString("address"),
                                        data.getString("price").toDouble(),
                                        data.getString("star"),
                                        0,
                                        1))
                                isinCart = itemFuncs.CheckProduct(itemid,this)
                                binding.InCartNow.text = "?????????? ???? ????????????"
                                binding.InCartNow.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_orange,null))
                            }
                        }
                    }
                    commonFuncs.hideLoadingDialog()
                }, Response.ErrorListener { error ->
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(activity,"?????? ???? ??????????????",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(activity,"?????? ???? ??????????????","?????? ?????? ????")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()

                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(this@FoodScreen, Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(activity, Constants.KeyUserToken)
                    }
                    return map
                }
            }
            val requestQueue = Volley.newRequestQueue(activity)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }
}
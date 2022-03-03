package com.example.funnytimesuserapp.SectionService

import android.app.Activity
import android.content.Intent
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
import com.example.funnytimesuserapp.Models.FTAttribute
import com.example.funnytimesuserapp.Models.FTClinicService
import com.example.funnytimesuserapp.Models.FTItemPhoto
import com.example.funnytimesuserapp.Models.FTReview
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.AttributesRecView
import com.example.funnytimesuserapp.RecViews.ItemGalleryRecView
import com.example.funnytimesuserapp.RecViews.ReviewRecView
import com.example.funnytimesuserapp.SectionService.SectionPayment.ServiceDatingScreen
import com.example.funnytimesuserapp.databinding.FtScreenChaletBinding
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class ChaletScreen : AppCompatActivity() {

    lateinit var binding: FtScreenChaletBinding
    val commonFuncs = CommonFuncs()
    val favouriteFuncs = FavoriteFuncs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenChaletBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val itemid = intent.getIntExtra("ItemId",0)
        Log.e("ProId",itemid.toString())

        binding.ChaletBack.setOnClickListener {
            finish()
        }


        item_Request(this,itemid)
    }
    fun item_Request(activity: Activity, itemid :Int){
        commonFuncs.showLoadingDialog(activity)
        val url = Constants.APIMain + "api/product/"+itemid
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONObject("data")

                    binding.ChaletName.text = data.getString("name").toString()
                    var is_favourite = data.getBoolean("is_favourite")
                    if (is_favourite){
                        binding.ChaletFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                    }else{
                        binding.ChaletFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
                    }
                    binding.ChaletFavorite.setOnClickListener {
                        if (commonFuncs.IsInSP(this, Constants.KeyUserToken)){
                            is_favourite = !is_favourite
                            if (is_favourite){
                                binding.ChaletFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                                favouriteFuncs.add_favourite_Request(this,itemid)
                            }else{
                                binding.ChaletFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
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
                        .into(binding.ChaletImage)
                    binding.ChaletCity.text = data.getString("address")
                    binding.ChaletDesc.text = data.getString("description")
                    binding.ChaletPrice.text = data.getString("price")
                    binding.ChaletDepositPrice.text = data.getString("deposit")
                    binding.VendorPolicies.setOnClickListener {
                        commonFuncs.showDefaultDialog(activity,"الشروط والأحكام",data.getString("policy").toString())
                    }
                    binding.ChaletRating.rating = data.getString("star").toFloat()
                    binding.ChaletRating.setOnTouchListener { _, _ ->
                        return@setOnTouchListener true
                    }
                    val gson = GsonBuilder().create()
                    val ftPhotos = ArrayList<FTItemPhoto>()
                    val ftReviews = ArrayList<FTReview>()
                    val ftAttributes = ArrayList<FTAttribute>()
                    ftPhotos.addAll(gson.fromJson(data.getJSONArray("gallery").toString(),Array<FTItemPhoto>::class.java).toList())
                    ftReviews.addAll(gson.fromJson(data.getJSONArray("reviews").toString(),Array<FTReview>::class.java).toList())
                    ftAttributes.addAll(gson.fromJson(data.getJSONArray("AttrabiuteProperty").toString(),Array<FTAttribute>::class.java).toList())


                    val attributesRecView = AttributesRecView(ftAttributes,this)
                    binding.ChaletAttRecycler.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL,
                        false)
                    binding.ChaletAttRecycler.adapter = attributesRecView
                    val itemGalleryRecView = ItemGalleryRecView(ftPhotos,this)
                    binding.ChaletGalleryRecycler.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL,
                        false)
                    binding.ChaletGalleryRecycler.adapter = itemGalleryRecView
                    val reviewRecView = ReviewRecView(ftReviews,this)
                    binding.ChaletReviewRecycler.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false)
                    binding.ChaletReviewRecycler.adapter = reviewRecView

                    val bookingType = data.getInt("bookingType")
                    binding.BookNow.setOnClickListener {
                        if (commonFuncs.IsInSP(this, Constants.KeyUserToken)){
                            val intent = Intent(this,ServiceDatingScreen::class.java)
                            intent.putExtra("itemid",itemid)
                            intent.putExtra("bookingType",bookingType)
                            intent.putExtra("price",data.getString("deposit").toDouble().toString())
                            startActivity(intent)
                        }else{
                            commonFuncs.showLoginDialog(this)
                        }
                    }

                    commonFuncs.hideLoadingDialog()
                }, Response.ErrorListener { error ->
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(activity,"خطأ في الاتصال",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(activity,"خطأ في الاتصال","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()

                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(this@ChaletScreen, Constants.KeyUserToken)){
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
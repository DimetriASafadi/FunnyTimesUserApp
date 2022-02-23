package com.example.funnytimesuserapp.SectionService

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Interfaces.OnClinicServiceClick
import com.example.funnytimesuserapp.MainMenuSection.FavouriteSection.FavoriteFuncs
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTClinicService
import com.example.funnytimesuserapp.Models.FTItemPhoto
import com.example.funnytimesuserapp.Models.FTReview
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.ClinicServicesRecView
import com.example.funnytimesuserapp.RecViews.ItemGalleryRecView
import com.example.funnytimesuserapp.RecViews.ReviewRecView
import com.example.funnytimesuserapp.SectionService.SectionPayment.ServiceDatingScreen
import com.example.funnytimesuserapp.SpinnerAdapters.SClinicServiceAdapter
import com.example.funnytimesuserapp.databinding.FtScreenClinicBinding
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class ClinicScreen : AppCompatActivity(), OnClinicServiceClick {

    lateinit var binding: FtScreenClinicBinding
    val commonFuncs = CommonFuncs()
    val favouriteFuncs = FavoriteFuncs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenClinicBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val itemid = intent.getIntExtra("ItemId",0)
        Log.e("ProId",itemid.toString())

        binding.ClinicBack.setOnClickListener {
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

                    binding.ClinicName.text = data.getString("name").toString()
                    var is_favourite = data.getBoolean("is_favourite")
                    if (is_favourite){
                        binding.ClinicFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                    }else{
                        binding.ClinicFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
                    }
                    binding.ClinicFavorite.setOnClickListener {
                        if (commonFuncs.IsInSP(this, Constants.KeyUserToken)){
                            is_favourite = !is_favourite
                            if (is_favourite){
                                binding.ClinicFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                                favouriteFuncs.add_favourite_Request(this,itemid)
                            }else{
                                binding.ClinicFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
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
                        .into(binding.ClinicImage)
                    binding.ClinicCity.text = data.getString("address")
                    binding.ClinicDesc.text = data.getString("description")
                    binding.ClinicRating.rating = data.getString("star").toFloat()
                    binding.ClinicRating.setOnTouchListener { _, _ ->
                        return@setOnTouchListener true
                    }
                    val gson = GsonBuilder().create()
                    val ftPhotos = ArrayList<FTItemPhoto>()
                    val ftReviews = ArrayList<FTReview>()
                    val ftServices = ArrayList<FTClinicService>()
                    ftPhotos.addAll(gson.fromJson(data.getJSONArray("gallery").toString(),Array<FTItemPhoto>::class.java).toList())
                    ftReviews.addAll(gson.fromJson(data.getJSONArray("reviews").toString(),Array<FTReview>::class.java).toList())
                    ftServices.addAll(gson.fromJson(data.getJSONArray("services").toString(),Array<FTClinicService>::class.java).toList())



                    val itemGalleryRecView = ItemGalleryRecView(ftPhotos,this)
                    binding.ClinicGalleryRecycler.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL,
                        false)
                    binding.ClinicGalleryRecycler.adapter = itemGalleryRecView
                    val reviewRecView = ReviewRecView(ftReviews,this)
                    binding.ClinicReviewsRecycler.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false)
                    binding.ClinicReviewsRecycler.adapter = reviewRecView

                    val clinicServicesRecView = ClinicServicesRecView(ftServices,this,this)
                    binding.ClinicServices.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false)
                    binding.ClinicServices.adapter = clinicServicesRecView

                    binding.BookNow.setOnClickListener {
                        val intent = Intent(this, ServiceDatingScreen::class.java)
                        intent.putExtra("itemid",itemid)
                        intent.putExtra("bookingType",2)
                        startActivity(intent)
                    }


//                    ClinicMapLocation





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
                    if (commonFuncs.IsInSP(this@ClinicScreen, Constants.KeyUserToken)){
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

    override fun OnClinicServiceClickListener(total: Double) {
        binding.ClinicDynamicPrice.text = total.toString()
    }
}
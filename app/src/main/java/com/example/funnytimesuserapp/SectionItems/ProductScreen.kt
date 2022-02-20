package com.example.funnytimesuserapp.SectionItems

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Interfaces.OnProAttributeContainerClick
import com.example.funnytimesuserapp.MainMenuSection.FavouriteSection.FavoriteFuncs
import com.example.funnytimesuserapp.Models.*
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.ItemGalleryRecView
import com.example.funnytimesuserapp.RecViews.ProAttrContainerRecView
import com.example.funnytimesuserapp.RecViews.ReviewRecView
import com.example.funnytimesuserapp.SectionVendor.VendorScreen
import com.example.funnytimesuserapp.databinding.FtScreenProductBinding
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class ProductScreen : AppCompatActivity(), OnProAttributeContainerClick {

    lateinit var binding: FtScreenProductBinding
    val commonFuncs = CommonFuncs()
    val favouriteFuncs = FavoriteFuncs()
    val itemFuncs = ItemsFuncs()
    var itemid = 0

    lateinit var proAttrContainerRecView :ProAttrContainerRecView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        itemid = intent.getIntExtra("ItemId",0)
        Log.e("ProId",itemid.toString())

        binding.ProductBack.setOnClickListener {
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
                    val vendor = data.getJSONObject("vendor")

                    binding.ProductName.text = data.getString("name").toString()
                    var is_favourite = data.getBoolean("is_favourite")
                    if (is_favourite){
                        binding.ProductFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                    }else{
                        binding.ProductFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
                    }
                    binding.ProductFavorite.setOnClickListener {
                        if (commonFuncs.IsInSP(this, Constants.KeyUserToken)){
                            is_favourite = !is_favourite
                            if (is_favourite){
                                binding.ProductFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                                favouriteFuncs.add_favourite_Request(this,itemid)
                            }else{
                                binding.ProductFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
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
                        .into(binding.ProductImage)
                    Glide.with(this)
                        .load(vendor.getString("img").toString())
                        .centerCrop()
                        .placeholder(R.drawable.ft_broken_image)
                        .into(binding.VendorImage)
                    binding.ProductCity.text = data.getString("address")
                    binding.ProductVendorName.text = vendor.getString("name")
                    val vendortype = data.getString("type")
                    val vendorid = vendor.getInt("id")
                    binding.ProductVendorName.setOnClickListener {
                        val intent = Intent(this, VendorScreen::class.java)
                        intent.putExtra("vendorid",vendorid)
                        intent.putExtra("vendortype",vendortype)
                        startActivity(intent)
                    }
                    binding.VendorImage.setOnClickListener {
                        val intent = Intent(this, VendorScreen::class.java)
                        intent.putExtra("vendorid",vendorid)
                        intent.putExtra("vendortype",vendortype)
                        startActivity(intent)
                    }
                    binding.ProductDesc.text = data.getString("description")
                    binding.ProductRating.rating = data.getString("star").toFloat()
                    binding.ProductRating.setOnTouchListener { _, _ ->
                        return@setOnTouchListener true
                    }
                    binding.ProductPrice.text = data.getString("price")
                    val gson = GsonBuilder().create()
                    val ftProAttrContainer = ArrayList<FTProAttrContainer>()
                    val attributes = data.getJSONArray("attributes")
                    val ftPhotos = ArrayList<FTItemPhoto>()
                    val ftReviews = ArrayList<FTReview>()
                    for (a in 0 until attributes.length())
                    {
                        val ftproAttributes = ArrayList<FTProAttribute>()
                        ftproAttributes.addAll(gson.fromJson(attributes.getJSONObject(a).getJSONArray("data").toString(),Array<FTProAttribute>::class.java).toList())
                        ftProAttrContainer.add(FTProAttrContainer(attributes.getJSONObject(a).getString("name").toString(),ftproAttributes))
                    }

                    ftPhotos.addAll(gson.fromJson(data.getJSONArray("gallery").toString(),Array<FTItemPhoto>::class.java).toList())
                    ftReviews.addAll(gson.fromJson(data.getJSONArray("reviews").toString(),Array<FTReview>::class.java).toList())


                    proAttrContainerRecView = ProAttrContainerRecView(ftProAttrContainer,this,this)
                    binding.ProAttrsRecycler.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false)
                    binding.ProAttrsRecycler.adapter = proAttrContainerRecView

                    val itemGalleryRecView = ItemGalleryRecView(ftPhotos,this)
                    binding.ProductGalleryRecycler.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL,
                        false)
                    binding.ProductGalleryRecycler.adapter = itemGalleryRecView
                    val reviewRecView = ReviewRecView(ftReviews,this)
                    binding.ProductReviewsRecycler.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false)
                    binding.ProductReviewsRecycler.adapter = reviewRecView



                    binding.InCartNow.setOnClickListener {
                        itemFuncs.AddToCart(this,
                            FTInCart(
                                itemid,
                                itemFuncs.GetProIdString(itemid,proAttrContainerRecView.getAllData()),
                                data.getString("name").toString(),
                                data.getString("img").toString(),
                                vendor.getString("name"),
                                data.getString("address"),
                                data.getString("price").toDouble(),
                                data.getString("star"),
                                0,
                                1,
                                itemFuncs.GetSelectedAttributes(proAttrContainerRecView.getAllData())
                            )
                        )
                        binding.InCartNow.text = "موجود في العربة"
                        binding.InCartNow.isClickable = false
                        binding.InCartNow.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_orange,null))
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
                    if (commonFuncs.IsInSP(this@ProductScreen, Constants.KeyUserToken)){
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

    override fun OnProAttributeContainerClickListener(data: ArrayList<FTProAttrContainer>) {
        if (itemFuncs.CheckProductAttributes(itemFuncs.GetProIdString(itemid,proAttrContainerRecView.getAllData()), this,itemFuncs.GetSelectedAttributesString(proAttrContainerRecView.getAllData()))){
            binding.InCartNow.text = "موجود في العربة"
            binding.InCartNow.isClickable = false
            binding.InCartNow.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_orange,null))
        }else{
            binding.InCartNow.text = "أضف للعربة"
            binding.InCartNow.isClickable = true
            binding.InCartNow.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_dark_blue,null))

        }
    }
}
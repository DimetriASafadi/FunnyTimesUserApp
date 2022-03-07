package com.example.funnytimesuserapp.MainMenuSection.HomeSection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Interfaces.OnFavoriteClick
import com.example.funnytimesuserapp.Models.FTBanner
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.RecViews.*
import com.example.funnytimesuserapp.databinding.FtMainHomeBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset


class FragHome : Fragment(), OnFavoriteClick {
    private var _binding: FtMainHomeBinding? = null
    private val binding get() = _binding!!
    val ftBanners = ArrayList<FTBanner>()
    val ftCategories = ArrayList<FTCategory>()
    val ftMostRented = ArrayList<FTItem>()
    val ftMostShoped = ArrayList<FTItem>()
    val ftMostDemanded = ArrayList<FTItem>()

    val commonFuncs = CommonFuncs()

    lateinit var bannerRecView : BannerRecView
    lateinit var categoriesRecView : CategoriesRecView
    lateinit var servicesBigVerticalRecView : ServicesBigVerticalRecView
    lateinit var itemNormalHorizontalRecView :ItemNormalHorizontalRecView
    lateinit var itemInsiderRecView : ItemInsiderRecView
    val pagerSnapHelper = PagerSnapHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FtMainHomeBinding.inflate(inflater, container, false)
        val view = binding.root



        bannerRecView = BannerRecView(ftBanners,requireContext())
        binding.HomeAdBannersRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.HomeAdBannersRecycler.adapter = bannerRecView
        pagerSnapHelper.attachToRecyclerView(binding.HomeAdBannersRecycler)


        categoriesRecView = CategoriesRecView(ftCategories,requireContext(),"",0)
        binding.HomeCategoriesRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.HomeCategoriesRecycler.adapter = categoriesRecView

        servicesBigVerticalRecView = ServicesBigVerticalRecView(ftMostRented,requireActivity(),this)
        binding.HomeServedRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.HomeServedRecycler.adapter = servicesBigVerticalRecView
        val pagerSnapHelper2 = PagerSnapHelper()
        pagerSnapHelper2.attachToRecyclerView(binding.HomeServedRecycler)


        itemNormalHorizontalRecView = ItemNormalHorizontalRecView(ftMostShoped,requireActivity(),this)
        binding.HomePurchasedRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,
            false)
        binding.HomePurchasedRecycler.adapter = itemNormalHorizontalRecView

        itemInsiderRecView = ItemInsiderRecView(ftMostDemanded,requireActivity(),this)
        binding.HomeDemandedRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.HomeDemandedRecycler.adapter = itemInsiderRecView
        val pagerSnapHelper3 = PagerSnapHelper()
        pagerSnapHelper3.attachToRecyclerView(binding.HomeDemandedRecycler)


//        home_Request()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun home_Request(){
        commonFuncs.showLoadingDialog(requireActivity())
        val url = Constants.APIMain + "api/home"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONObject("data")
                    val ads = data.getJSONArray("ads")
                    val categories = data.getJSONArray("categories")
                    val mostBooking = data.getJSONArray("mostBooking")
                    val mostOrder = data.getJSONArray("mostOrder")
                    val mostShopping = data.getJSONArray("mostShopping")

                    ftBanners.clear()
                    ftCategories.clear()
                    ftMostRented.clear()
                    ftMostShoped.clear()
                    ftMostDemanded.clear()

                    val gson = GsonBuilder().create()
                    ftBanners.addAll(gson.fromJson(ads.toString(),Array<FTBanner>::class.java).toList())
                    ftCategories.add(FTCategory(0,"الكل","","",""))
                    ftCategories.addAll(gson.fromJson(categories.toString(),Array<FTCategory>::class.java).toList())
                    ftMostRented.addAll(gson.fromJson(mostBooking.toString(),Array<FTItem>::class.java).toList())
                    ftMostShoped.addAll(gson.fromJson(mostOrder.toString(),Array<FTItem>::class.java).toList())
                    ftMostDemanded.addAll(gson.fromJson(mostShopping.toString(),Array<FTItem>::class.java).toList())

                    bannerRecView.notifyDataSetChanged()
                    categoriesRecView.notifyDataSetChanged()
                    servicesBigVerticalRecView.notifyDataSetChanged()
                    itemNormalHorizontalRecView.notifyDataSetChanged()
                    itemInsiderRecView.notifyDataSetChanged()

                    binding.BannerIndicator.attachToRecyclerView(binding.HomeAdBannersRecycler,pagerSnapHelper)

                    commonFuncs.hideLoadingDialog()

                }, Response.ErrorListener { error ->
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(requireContext(),"خطأ في الإتصال",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(requireContext(),"خطأ في الإتصال","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()

                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(requireContext(), Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(requireContext(), Constants.KeyUserToken)
                        Log.e("HomeToken",commonFuncs.GetFromSP(requireContext(), Constants.KeyUserToken).toString())
                    }
                    return map
                }
            }
            val requestQueue = Volley.newRequestQueue(requireContext())
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }


    override fun onResume() {
        super.onResume()
            home_Request()
    }



    override fun OnFavoriteClickListener(item: FTItem) {
        for (i in 0 until ftMostRented.size) {
            if (ftMostRented[i].ItemId == item.ItemId){
                ftMostRented[i].ItemIsFavorite = item.ItemIsFavorite
            }
        }
        for (i in 0 until ftMostShoped.size) {
            if (ftMostShoped[i].ItemId == item.ItemId){
                ftMostShoped[i].ItemIsFavorite = item.ItemIsFavorite
            }
        }

        for (i in 0 until ftMostDemanded.size) {
            if (ftMostDemanded[i].ItemId == item.ItemId){
                ftMostDemanded[i].ItemIsFavorite = item.ItemIsFavorite
            }
        }
        servicesBigVerticalRecView.notifyDataSetChanged()
        itemNormalHorizontalRecView.notifyDataSetChanged()
        itemInsiderRecView.notifyDataSetChanged()
    }

}
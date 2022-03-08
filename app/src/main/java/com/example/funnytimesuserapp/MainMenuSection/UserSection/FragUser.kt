package com.example.funnytimesuserapp.MainMenuSection.UserSection

import android.app.Activity
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Models.FTBook
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTOrder
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.BooksRecView
import com.example.funnytimesuserapp.RecViews.OrdersRecView
import com.example.funnytimesuserapp.databinding.FtMainUserBinding
import com.google.gson.GsonBuilder
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class FragUser : Fragment() {
    private var _binding: FtMainUserBinding? = null
    private val binding get() = _binding!!

    val commonFuncs = CommonFuncs()

    val ftBooks = ArrayList<FTBook>()
    val ftOrders = ArrayList<FTOrder>()

    lateinit var booksRecView: BooksRecView
    lateinit var ordersRecView: OrdersRecView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FtMainUserBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.BooksButton.setOnClickListener {
            binding.BooksButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.BooksTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_dark_blue,null))
            binding.OrdersButton.setBackgroundResource(0)
            binding.OrdersTextImg.setTextColor(resources.getColor(R.color.ft_grey_1,null))
            binding.OrdersTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_grey_1,null))
            binding.SwipeBooks.visibility = View.VISIBLE
            binding.SwipeOrders.visibility = View.INVISIBLE
        }
        binding.OrdersButton.setOnClickListener {
            binding.OrdersButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.OrdersTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.OrdersTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_dark_blue,null))
            binding.BooksButton.setBackgroundResource(0)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_grey_1,null))
            binding.BooksTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_grey_1,null))
            binding.SwipeBooks.visibility = View.INVISIBLE
            binding.SwipeOrders.visibility = View.VISIBLE
        }

        binding.SwipeBooks.setOnRefreshListener(SwipyRefreshLayout.OnRefreshListener { direction ->
            if (direction == SwipyRefreshLayoutDirection.TOP) {
                Log.e("SwipeBooks","Top")
            }
        })
        binding.SwipeOrders.setOnRefreshListener(SwipyRefreshLayout.OnRefreshListener { direction ->
            if (direction == SwipyRefreshLayoutDirection.TOP) {
                Log.e("SwipeOrders","Top")
            }
        })


        if (commonFuncs.GetFromSP(requireContext(),"PurchasedFrom") == "Order"){
            commonFuncs.DeleteFromSP(requireContext(),"PurchasedFrom")
            binding.OrdersButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.OrdersTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.OrdersTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_dark_blue,null))
            binding.BooksButton.setBackgroundResource(0)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_grey_1,null))
            binding.BooksTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_grey_1,null))
            binding.SwipeBooks.visibility = View.INVISIBLE
            binding.SwipeOrders.visibility = View.VISIBLE


        }else if (commonFuncs.GetFromSP(requireContext(),"PurchasedFrom") == "Service"){
            commonFuncs.DeleteFromSP(requireContext(),"PurchasedFrom")
            binding.BooksButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.BooksTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_dark_blue,null))
            binding.OrdersButton.setBackgroundResource(0)
            binding.OrdersTextImg.setTextColor(resources.getColor(R.color.ft_grey_1,null))
            binding.OrdersTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_grey_1,null))
            binding.SwipeBooks.visibility = View.VISIBLE
            binding.SwipeOrders.visibility = View.INVISIBLE

        }

        booksRecView = BooksRecView(ftBooks,requireContext())
        ordersRecView = OrdersRecView(ftOrders,requireContext())
        binding.OrdersRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,
            false)
        binding.BooksRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,
            false)

        binding.OrdersRecycler.adapter = ordersRecView
        binding.BooksRecycler.adapter = booksRecView

        get_Books_Request(requireActivity())
        get_Orders_Request(requireActivity())
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun get_Books_Request(activity: Activity){
        val url = Constants.APIMain + "api/book/get"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONArray("data")
                    val gson = GsonBuilder().create()
                    ftBooks.clear()
                    ftBooks.addAll(gson.fromJson(data.toString(),Array<FTBook>::class.java).toList())
                    Log.e("ftbooks", ftBooks.toString())
                    booksRecView.notifyDataSetChanged()
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

                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(requireContext(), Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(activity, Constants.KeyUserToken)
                    }
                    return map
                }
            }
            val requestQueue = Volley.newRequestQueue(activity)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
        }
    }
    fun get_Orders_Request(activity: Activity){
        val url = Constants.APIMain + "api/order/"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONArray("data")
                    val gson = GsonBuilder().create()
                    ftOrders.clear()
                    ftOrders.addAll(gson.fromJson(data.toString(),Array<FTOrder>::class.java).toList())
                    Log.e("ftOrders", ftOrders.toString())
                    ordersRecView.notifyDataSetChanged()
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

                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(requireContext(), Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(activity, Constants.KeyUserToken)
                    }
                    return map
                }
            }
            val requestQueue = Volley.newRequestQueue(activity)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
        }
    }





}
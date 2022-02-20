package com.example.funnytimesuserapp.SectionVendor

import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTSortType
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.ItemInsider2RecView
import com.example.funnytimesuserapp.SpinnerAdapters.SMiniFilterSortAdapter
import com.example.funnytimesuserapp.databinding.FtScreenVendorBinding
import com.google.gson.GsonBuilder
import com.jaygoo.widget.RangeSeekBar
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset


class VendorScreen : AppCompatActivity() {

    lateinit var binding: FtScreenVendorBinding
    val vendorItems = ArrayList<FTItem>()
    val sortTypes = ArrayList<FTSortType>()

    var minifilterDialog:Dialog? = null
    lateinit var itemInsider2RecView : ItemInsider2RecView
    lateinit var sMiniFilterSortAdapter: SMiniFilterSortAdapter

    val commonFuncs = CommonFuncs()

    var filtersort = ""
    var filtermax = ""
    var filtermin = ""
    var filtername = ""

    var vendorid = 0
    var vendortype = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenVendorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        vendorid = intent.getIntExtra("vendorid",0)
        vendortype = intent.getStringExtra("vendortype").toString()

        sortTypes.add(FTSortType("الأشهر","most"))
        sortTypes.add(FTSortType("الأحدث","recent"))
        sortTypes.add(FTSortType("الأعلى تقييماً","top"))

        SetUpVendorFilterDialog(this)
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.VendorName.text = " "


        itemInsider2RecView = ItemInsider2RecView(vendorItems,this)
        binding.VendorItemsRecycler.layoutManager = GridLayoutManager(this,2)
        binding.VendorItemsRecycler.adapter = itemInsider2RecView

        binding.SearchFilter.setOnClickListener {
            if (minifilterDialog != null && !minifilterDialog!!.isShowing){
                minifilterDialog!!.show()
            }
        }

        binding.VendorSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                filtername = binding.VendorSearch.text.toString()
                vendor_Request(vendortype.toString(),vendorid,1)
                return@OnEditorActionListener true
            }
            false
        })

        vendor_Request(vendortype.toString(),vendorid,3)


    }

    fun SetUpVendorFilterDialog(activity: Activity) {
        minifilterDialog = Dialog(activity)
        minifilterDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        minifilterDialog?.setCancelable(true)
        minifilterDialog?.setContentView(R.layout.ft_dialog_mini_filter)
        val rangeseek = minifilterDialog?.findViewById<RangeSeekBar>(R.id.PriceRangeBar)
        val BarMax = minifilterDialog?.findViewById<TextView>(R.id.BarMax)
        val BarMin = minifilterDialog?.findViewById<TextView>(R.id.BarMin)
        rangeseek?.setOnRangeChangedListener { view, min, max, isFromUser ->
            BarMax?.text = max.toInt().toString()+" ر.س"
            BarMin?.text = min.toInt().toString()+" ر.س"
            filtermax = max.toInt().toString()
            filtermin = min.toInt().toString()
        }
        val ShowResults = minifilterDialog?.findViewById<TextView>(R.id.ShowResults)
        val MiniFilterName = minifilterDialog?.findViewById<EditText>(R.id.MiniFilterName)
        val ClearFilter = minifilterDialog?.findViewById<TextView>(R.id.ClearFilter)
        val SortTypesSpinner = minifilterDialog?.findViewById<Spinner>(R.id.SortTypesSpinner)
        MiniFilterName!!.addTextChangedListener {
            filtername = MiniFilterName.text.toString()
        }
        ShowResults!!.setOnClickListener {
            if (minifilterDialog!!.isShowing){
                minifilterDialog!!.dismiss()
            }
            vendor_Request(vendortype.toString(),vendorid,0)
        }
        ClearFilter!!.setOnClickListener {
            MiniFilterName.setText("")
            rangeseek!!.setValue(0f,0f)
            if (minifilterDialog!!.isShowing){
                minifilterDialog!!.dismiss()
            }
            vendor_Request(vendortype.toString(),vendorid,3)
        }
        sMiniFilterSortAdapter = SMiniFilterSortAdapter(this,sortTypes)
        SortTypesSpinner!!.adapter = sMiniFilterSortAdapter
        SortTypesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.e("SelectedService", sMiniFilterSortAdapter.getItem(position).toString())
                filtersort =sMiniFilterSortAdapter.getItem(position)!!.SortEnName
            }
        }


        val window: Window = minifilterDialog?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(activity.resources
                .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }


    fun vendor_Request(vendorType:String,vendorId:Int,withWhat:Int){
        var url = ""
        if (withWhat == 0){
            url = Constants.APIMain + "api/vendor/"+vendorId+"/"+vendorType+"?"+"sortBy=$filtersort&name=$filtername&price[from]=$filtermin&price[to]=$filtermax"
        }else if (withWhat == 1){
            url = Constants.APIMain + "api/vendor/"+vendorId+"/"+vendorType+"?"+"name=$filtername"
        }else{
            url = Constants.APIMain + "api/vendor/"+vendorId+"/"+vendorType
        }

        Log.e("params",filtersort+filtermin+filtermax+filtername)
        commonFuncs.showLoadingDialog(this)
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONObject("data")
                    val vendor = data.getJSONObject("vendor")
                    val products = data.getJSONArray("products")
                    val gson = GsonBuilder().create()
                    vendorItems.clear()
                    vendorItems.addAll(gson.fromJson(products.toString(),Array<FTItem>::class.java).toList())
                    binding.VendorName.text = vendor.getString("name")
                    itemInsider2RecView.notifyDataSetChanged()
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
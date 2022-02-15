package com.example.funnytimesuserapp.SectionService

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.databinding.FtScreenChaletBinding
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class ChaletScreen : AppCompatActivity() {

    lateinit var binding: FtScreenChaletBinding
    val commonFuncs = CommonFuncs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenChaletBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val itemid = intent.getIntExtra("ItemId",0)


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
                    map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(activity, Constants.KeyUserToken)
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
package com.example.funnytimesuserapp.SectionService.SectionPayment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Models.FTBank
import com.example.funnytimesuserapp.RecViews.BankRecView
import com.example.funnytimesuserapp.databinding.FtScreenPaymentBinding
import com.google.gson.GsonBuilder
import com.theartofdev.edmodo.cropper.CropImage
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset


class PaymentScreen : AppCompatActivity() {

    var chosenpic = 0
    lateinit var chosenpicuri: Uri
    var selfBookId = ""
    var selfBankId = ""


    val commonFuncs = CommonFuncs()
    lateinit var binding: FtScreenPaymentBinding
    lateinit var bankRecView: BankRecView
    val ftbanks = ArrayList<FTBank>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backBtn.setOnClickListener {
            finish()
        }

        bankRecView = BankRecView(ftbanks,this)
        binding.BanksRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)
        binding.BanksRecycler.adapter = bankRecView

        binding.PickImage.setOnClickListener {
            pickimage()
        }

        banks_Request()
    }


    fun pickimage() {
        CropImage.activity().start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                var mImageuri = result.uri
                chosenpicuri = result.uri
                binding.BankTransPhoto.setImageURI(mImageuri)
                chosenpic = 1
                Log.e("Error","Working")
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.e("Error","Not Working")
                chosenpic = 0
            }
        }
    }

    fun banks_Request(){
        val url = Constants.APIMain + "api/banking"

        commonFuncs.showLoadingDialog(this)
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONArray("data")
                    val gson = GsonBuilder().create()
                    ftbanks.clear()
                    ftbanks.addAll(gson.fromJson(data.toString(),Array<FTBank>::class.java).toList())
                    bankRecView.notifyDataSetChanged()
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
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(this@PaymentScreen, Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(this@PaymentScreen, Constants.KeyUserToken)
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
}
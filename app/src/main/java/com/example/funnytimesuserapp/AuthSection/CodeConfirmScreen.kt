package com.example.funnytimesuserapp.AuthSection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.CommonSection.Constants.KeyUserID
import com.example.funnytimesuserapp.CommonSection.Constants.KeyUserToken
import com.example.funnytimesuserapp.MainMenu
import com.example.funnytimesuserapp.databinding.FtCodeConfirmScreenBinding
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.HashMap

class CodeConfirmScreen : AppCompatActivity() {

    lateinit var binding : FtCodeConfirmScreenBinding
    val commonFuncs = CommonFuncs()

    var temptoken = ""
    var comingFrom = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCodeConfirmScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        temptoken = intent.getStringExtra("TempToken").toString()
        comingFrom = intent.getStringExtra("comingFrom").toString()

        binding.CodeResend.setOnClickListener {
            generate_code_Request()
        }

        if (comingFrom == "signin"){
            binding.CodeResend.isClickable = false
            generate_code_Request()
        }

        Log.e("tempToken",temptoken.toString())

        binding.CheckCode.setOnClickListener {
            if (binding.CodePinET.text.toString().isNullOrEmpty()){
                binding.CodePinET.error = "أدخل كود التفعيل"
                return@setOnClickListener
            }
            verify_Request(binding.CodePinET.text.toString())
        }
        binding.CodePinET.addTextChangedListener {
            if (binding.CodePinET.text?.length == 4 ){
                verify_Request(binding.CodePinET.text.toString())
            }
        }






    }

    fun verify_Request(code:String) {
        commonFuncs.showLoadingDialog(this)
        val url = Constants.APIMain + "api/auth/verify"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.POST, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val data = JSONObject(response.toString()).getJSONObject("data")
                    val userid = data.getJSONObject("user").getInt("id")
                    commonFuncs.WriteOnSP(this,KeyUserToken,temptoken)
                    commonFuncs.WriteOnSP(this,KeyUserID,userid.toString())
                    commonFuncs.hideLoadingDialog()
                    commonFuncs.showCodeDoneDialog(this)
                }, Response.ErrorListener { error ->
                    Log.e("Error", error.toString())
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(this,"فشل التحقق",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(this,"فشل التحقق","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()

                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String,String>()
                    params["code"] = code
                    return params
                }
                override fun getHeaders(): MutableMap<String, String> {
                    val header = HashMap<String,String>()
                    header["Authorization"] = "Bearer $temptoken"
                    return header
                }
            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }

    fun generate_code_Request(){
        Log.e(comingFrom,"SentCode")
        PrepareResend()
        commonFuncs.showLoadingDialog(this)
        val url = Constants.APIMain + "api/auth/generateCode"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.POST, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())

                    commonFuncs.hideLoadingDialog()
                }, Response.ErrorListener { error ->
                    Log.e("Error", error.toString())
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(this,"فشل ارسال الرمز",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(this,"فشل ارسال الرمز","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val header = HashMap<String,String>()
                    header["Authorization"] = "Bearer $temptoken"
                    return header
                }
            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }

    fun PrepareResend(){
        binding.CodeResend.isClickable = false
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.CodeResend.text = "إعادة ارسال الرمز متاحة بعد"+" "+(millisUntilFinished/1000)+" "+"ثانية"
            }
            override fun onFinish() {
                binding.CodeResend.text = "إعادة ارسال رمز التفعيل"
                binding.CodeResend.isClickable = true
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (commonFuncs.codePhone != null){
            if (commonFuncs.codePhone!!.isShowing){
                commonFuncs.codePhone!!.dismiss()
            }
        }
    }



}
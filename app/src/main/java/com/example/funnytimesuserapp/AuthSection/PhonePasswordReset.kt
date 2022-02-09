package com.example.funnytimesuserapp.AuthSection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.databinding.FtPhonePasswordResetBinding
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class PhonePasswordReset : AppCompatActivity() {

    lateinit var binding: FtPhonePasswordResetBinding
    val  commonFuncs = CommonFuncs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtPhonePasswordResetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.ResetCountryPicker.registerCarrierNumberEditText(binding.ResetPhoneNumber)
        binding.SendResetToPhone.setOnClickListener {
            if (binding.ResetPhoneNumber.text.toString().isNullOrEmpty()){
                binding.ResetPhoneNumber.error = "لا يمكن ترك الحقل فارغ"
                binding.ResetPhoneNumber.requestFocus()
                return@setOnClickListener
            }
            reset_Request(binding.ResetCountryPicker.fullNumberWithPlus.toString())
        }

        binding.ClearResetPhoneNum.setOnClickListener {
            binding.ResetPhoneNumber.setText("")
        }

        binding.Back.setOnClickListener {
            finish()
        }




    }

    fun reset_Request(phonenum:String) {
        Log.e("PhoneNumber",phonenum)
        commonFuncs.showLoadingDialog(this)
        val url = Constants.APIMain + "api/auth/reset"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.POST, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val data = JSONObject(response.toString()).getJSONObject("status")
                    val status = data.getString("status").toBoolean()
                    if (status){
                        commonFuncs.hideLoadingDialog()
                        val intent = Intent(this,PassResetCodeConfirmScreen::class.java)
                        intent.putExtra("SendToPhone",phonenum)
                        startActivity(intent)
                        finish()
                    }else{
                        commonFuncs.showDefaultDialog(this,"فشل إعادة التعيين","حصل خطأ ما")
                    }
                }, Response.ErrorListener { error ->
                    Log.e("Error", error.toString())
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(this,"فشل إعادة التعيين",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(this,"فشل إعادة التعيين","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()

                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String,String>()
                    params["phone"] = phonenum
                    return params
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
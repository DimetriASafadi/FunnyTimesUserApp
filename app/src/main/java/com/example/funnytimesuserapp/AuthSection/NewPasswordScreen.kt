package com.example.funnytimesuserapp.AuthSection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.databinding.FtNewPasswordScreenBinding
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset
import kotlin.collections.HashMap

class NewPasswordScreen : AppCompatActivity() {


    lateinit var binding: FtNewPasswordScreenBinding
    val commonFuncs = CommonFuncs()
    var phonenum = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtNewPasswordScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        phonenum = intent.getStringExtra("SendToPhone").toString()

        binding.ResetPassword.setOnClickListener {

            val rPassword = binding.NewPassword.text.toString()
            val rCPassword = binding.NewPasswordConfirm.text.toString()

            if (rPassword.isNullOrEmpty()){
                binding.NewPassword.error = "لا يمكن ترك الحقل فارغ"
                binding.NewPassword.requestFocus()
                return@setOnClickListener
            }
            if (rCPassword.isNullOrEmpty()){
                binding.NewPasswordConfirm.error = "لا يمكن ترك الحقل فارغ"
                binding.NewPasswordConfirm.requestFocus()
                return@setOnClickListener
            }
            if (rPassword != rCPassword ){
                binding.NewPasswordConfirm.error = "كلمات السر غير متطابقة"
                binding.NewPasswordConfirm.requestFocus()
                return@setOnClickListener
            }

            resetPassword_Request(rPassword)
        }

    }

    fun resetPassword_Request(password:String) {
        commonFuncs.showLoadingDialog(this)
        val url = Constants.APIMain + "api/auth/resetPassword"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.POST, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val statusdata = JSONObject(response.toString()).getJSONObject("status")
                    val data = JSONObject(response.toString()).getJSONObject("data")
                    val status = statusdata.getString("status").toBoolean()
                    val token = data.getString("access_token")
                    commonFuncs.WriteOnSP(this, Constants.KeyUserToken,token)

                    if (status){
                        commonFuncs.hideLoadingDialog()
                        commonFuncs.showPasswordDoneDialog(this)
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
                    params["password"] = password
                    params["Cpassword"] = password
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

    override fun onDestroy() {
        super.onDestroy()

        if (commonFuncs.passwordDoneDialog != null){
            if (commonFuncs.passwordDoneDialog!!.isShowing){
                commonFuncs.passwordDoneDialog!!.dismiss()
            }
        }
    }

}
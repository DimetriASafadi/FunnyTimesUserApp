package com.example.funnytimesuserapp.AuthSection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.databinding.FtSignUpScreenBinding
import java.nio.charset.Charset

class SignUpScreen : AppCompatActivity() {

    lateinit var binding: FtSignUpScreenBinding
    val commonFuncs = CommonFuncs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FtSignUpScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.SUGoToSignIn.setOnClickListener {
            startActivity(Intent(this,SignInScreen::class.java))
            finish()
        }
        binding.SUReadServicePolicies.setOnClickListener {

        }
        binding.SUReadPrivacyPolicy.setOnClickListener {

        }
        binding.SignUpBtn.setOnClickListener {
            val suEmail = binding.SUEmail.text.toString()
            val suPassword = binding.SUPassword.text.toString()
            val suCPassword = binding.SUConfirmPassword.text.toString()
            if (suEmail.isNullOrEmpty()){
                binding.SUEmail.error = "لا يمكن ترك الحقل فارغ"
                binding.SUEmail.requestFocus()
                return@setOnClickListener
            }
            if (suPassword.isNullOrEmpty()){
                binding.SUPassword.error = "لا يمكن ترك الحقل فارغ"
                binding.SUPassword.requestFocus()
                return@setOnClickListener
            }
            if (suCPassword.isNullOrEmpty()){
                binding.SUConfirmPassword.error = "لا يمكن ترك الحقل فارغ"
                binding.SUConfirmPassword.requestFocus()
                return@setOnClickListener
            }
            if (commonFuncs.isValidEmail(suEmail)){
                binding.SUConfirmPassword.error = "تأكد من صحة البريد الإلكتروني"
                binding.SUConfirmPassword.requestFocus()
                return@setOnClickListener
            }
            if (suPassword != suCPassword ){
                binding.SUConfirmPassword.error = "كلمات السر غير متطابقة"
                binding.SUConfirmPassword.requestFocus()
                return@setOnClickListener
            }
            sign_up_Request(suEmail,suPassword)
        }
    }
    fun sign_up_Request(username:String,password:String) {
        commonFuncs.showLoadingDialog(this)
        val url = Constants.APIMain + "api/auth/signup"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.POST, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    startActivity(Intent(this,PhoneConfirmScreen::class.java))
                    finish()
                    commonFuncs.hideLoadingDialog()
                }, Response.ErrorListener { error ->
                    Log.e("Error", error.toString())
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        Log.e("eResponser", errorw.toString())
                    } else {
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()

                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String,String>()
                    params["email"] = username
                    params["password"] = password
                    params["password_confirmation"] = password
                    return params
                }
//            override fun getHeaders(): MutableMap<String, String> {
//
//                return headers
//            }
            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }catch (error: VolleyError){
            Log.e("Response", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }
}
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
import com.example.funnytimesuserapp.databinding.FtSignInScreenBinding
import java.lang.Exception
import java.nio.charset.Charset

class SignInScreen : AppCompatActivity() {
    lateinit var binding: FtSignInScreenBinding
    val commonFuncs = CommonFuncs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtSignInScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.SSignIn.setOnClickListener {
            val username = binding.SUserEmail.text.toString()
            val password = binding.SUserPassword.text.toString()
            if (username.isNullOrEmpty()){
                binding.SUserEmail.error = "لا يمكن ترك الحقل فارغ"
                binding.SUserEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()){
                binding.SUserEmail.error = "لا يمكن ترك الحقل فارغ"
                binding.SUserEmail.requestFocus()
                return@setOnClickListener
            }
            login_Request(username,password)
        }
        binding.SForgotPass.setOnClickListener {

        }
        binding.SGoToRegister.setOnClickListener {
            startActivity(Intent(this,SignUpScreen::class.java))
            finish()
        }
    }
    fun login_Request(username:String,password:String) {
        commonFuncs.showLoadingDialog(this)
        val url = Constants.APIMain + "api/auth/login"
        try {
        val stringRequest = object : StringRequest(
            Request.Method.POST, url, Response.Listener<String> { response ->
                Log.e("Response", response.toString())
                commonFuncs.hideLoadingDialog()
            }, Response.ErrorListener { error ->
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
                return params
            }
//            override fun getHeaders(): MutableMap<String, String> {
//
//                return headers
//            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
        }catch (error:VolleyError){
            Log.e("Response", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }
}
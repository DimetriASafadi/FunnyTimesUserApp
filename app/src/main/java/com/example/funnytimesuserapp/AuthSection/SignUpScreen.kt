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
import com.example.funnytimesuserapp.CommonSection.Constants.APIMain
import com.example.funnytimesuserapp.CommonSection.Constants.KeyUserToken
import com.example.funnytimesuserapp.MainMenu
import com.example.funnytimesuserapp.databinding.FtSignUpScreenBinding
import org.json.JSONException
import org.json.JSONObject
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

            if (!binding.SUAcceptPolicies.isChecked){
                commonFuncs.showDefaultDialog(this,"فشل التسجيل","يجب ان توافق على الشروط والسياسات")
                return@setOnClickListener
            }

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
//            if (commonFuncs.isValidEmail(suEmail)){
//                binding.SUEmail.error = "تأكد من صحة البريد الإلكتروني"
//                binding.SUEmail.requestFocus()
//                return@setOnClickListener
//            }

            if (suPassword != suCPassword ){
                binding.SUConfirmPassword.error = "كلمات السر غير متطابقة"
                binding.SUConfirmPassword.requestFocus()
                return@setOnClickListener
            }
            sign_up_Request(suEmail,suPassword)
        }

        binding.ContAsGuest.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
    fun sign_up_Request(username:String,password:String) {
        commonFuncs.showLoadingDialog(this)
        val url = APIMain + "api/auth/signup"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.POST, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val data = JSONObject(response.toString()).getJSONObject("data")
                    val tempToken = data.getString("access_token")
                    val intent = Intent(this,PhoneConfirmScreen::class.java)
                    intent.putExtra("TempToken",tempToken)
                    intent.putExtra("comingFrom","signup")
                    startActivity(intent)
                    finish()
                    commonFuncs.hideLoadingDialog()
                }, Response.ErrorListener { error ->
                    Log.e("Error", error.toString())
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(this,"فشل التسجيل",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(this,"فشل التسجيل","حصل خطأ ما")
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
        }catch (error: JSONException){
            Log.e("Response", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }
}
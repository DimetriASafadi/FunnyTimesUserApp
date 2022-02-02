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
import com.example.funnytimesuserapp.databinding.FtPhoneConfirmScreenBinding
import org.json.JSONObject
import java.nio.charset.Charset

class PhoneConfirmScreen : AppCompatActivity() {


    lateinit var binding : FtPhoneConfirmScreenBinding
//    val CountriesArray = ArrayList<String>()
    val commonFuncs = CommonFuncs()

    var temptoken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtPhoneConfirmScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        temptoken = intent.getStringExtra("TempToken").toString()

//        CountriesArray.add("+966")
//        CountriesArray.add("+962")


        binding.CountryPicker.registerCarrierNumberEditText(binding.PhoneNumber)

        binding.SendCodeToPhone.setOnClickListener {
            Log.e("Phone",binding.CountryPicker.fullNumberWithPlus.toString())

            if (binding.PhoneNumber.text.toString().isNullOrEmpty()){
                binding.PhoneNumber.error = "لا يمكن ترك الحقل فارغ"
                binding.PhoneNumber.requestFocus()
                return@setOnClickListener
            }
            update_phone_Request(binding.CountryPicker.fullNumberWithPlus.toString())
        }
//        binding.PhoCountryCodesSpin.adapter = SPhoneAuthCountryAdapter(this, CountriesArray)
//        binding.PhoCountryCodesSpin.onItemSelectedListener
//        binding.PhoCountryCodesSpin?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {s
////                binding.PhoCountryCode.text = CountriesHash.get(PhoCountryCodesSpin.selectedItem.toString())?.countryphoneCode
//            }
//        }
    }

    fun update_phone_Request(phonenum:String) {
        commonFuncs.showLoadingDialog(this)
        val url = Constants.APIMain + "api/auth/add/phone"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.POST, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val data = JSONObject(response.toString()).getJSONObject("data")
                    val tempToken = data.getString("access_token")
                    val intent = Intent(this,PhoneConfirmScreen::class.java)
                    intent.putExtra(Constants.KeyUserToken,tempToken)
                    startActivity(intent)
                    commonFuncs.hideLoadingDialog()
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
                    params["phone"] = phonenum
                    return params
                }
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String,String>()
                header["Authorization"] = temptoken
                return header
            }
            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }catch (error: VolleyError){
            Log.e("Response", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }
}
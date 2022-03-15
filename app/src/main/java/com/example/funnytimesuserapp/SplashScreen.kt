package com.example.funnytimesuserapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.AuthSection.SignInScreen
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.CommonSection.Constants.FilterTools
import com.example.funnytimesuserapp.CommonSection.Constants.KeyAppLanguage
import com.example.funnytimesuserapp.CommonSection.Constants.KeyOpenBefore
import com.example.funnytimesuserapp.CommonSection.Constants.KeyUserToken
import com.example.funnytimesuserapp.SplashSection.SplashMenu
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

import java.util.*


class SplashScreen : AppCompatActivity() {

    val commonFuncs = CommonFuncs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (commonFuncs.IsInSP(this,KeyAppLanguage)){
            commonFuncs.setLocale2(this,commonFuncs.GetFromSP(this,KeyAppLanguage)!!)
        }else{
            commonFuncs.setLocale2(this,"ar")
        }
        commonFuncs.DeleteFromSP(this,"FavoriteChanged")
        setContentView(R.layout.ft_splash_screen)
        filterTools_Request()

    }
    fun finishSplashToMain(){
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashScreen, MainMenu::class.java).apply {}
                startActivity(intent)
                finish()
            }
        }, 2000)
    }
    fun finishSplashToLog(){
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashScreen, SignInScreen::class.java).apply {}
                startActivity(intent)
                finish()
            }
        }, 2000)
    }
    fun finishSplashToOnBoard(){
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashScreen, SplashMenu::class.java).apply {}
                startActivity(intent)
                finish()
            }
        }, 2000)
    }

    override fun onResume() {
        super.onResume()
        if (commonFuncs.IsInSP(this,KeyAppLanguage)){
            commonFuncs.setLocale2(this,commonFuncs.GetFromSP(this,KeyAppLanguage)!!)
        }else{
            commonFuncs.setLocale2(this,"ar")
        }
    }

    fun filterTools_Request(){
        var url = Constants.APIMain + "api/filter/get"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONObject("data")
                    commonFuncs.WriteOnSP(this,FilterTools,data.toString())
                    if (commonFuncs.GetFromSP(this,KeyOpenBefore) == "Yes"){
                        if (commonFuncs.GetFromSP(this,KeyUserToken) != "NoValue"){
                            finishSplashToMain()
                        }else{
                            finishSplashToLog()
                        }
                    }else{
                        finishSplashToOnBoard()
                    }
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
                }) {
            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
        }
    }


}
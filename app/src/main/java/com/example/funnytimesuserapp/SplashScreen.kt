package com.example.funnytimesuserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.funnytimesuserapp.AuthSection.SignInScreen
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants.KeyAppLanguage
import com.example.funnytimesuserapp.CommonSection.Constants.KeyOpenBefore
import com.example.funnytimesuserapp.CommonSection.Constants.KeyUserToken
import com.example.funnytimesuserapp.SplashSection.SplashMenu
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
        setContentView(R.layout.ft_splash_screen)
        if (commonFuncs.GetFromSP(this,KeyOpenBefore) == "Yes"){
            if (commonFuncs.GetFromSP(this,KeyUserToken) != "NoValue"){
                finishSplashToMain()
            }else{
                finishSplashToLog()
            }
        }else{
            finishSplashToOnBoard()
        }
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
}
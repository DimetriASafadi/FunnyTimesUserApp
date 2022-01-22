package com.example.funnytimesuserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.funnytimesuserapp.SplashSection.SplashMenu
import java.util.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ft_splash_screen)

        finishSplashToLog()



    }

    fun finishSplashToLog(){
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashScreen, SplashMenu::class.java).apply {}
                startActivity(intent)
                finish()
            }
        }, 2000)
    }
}
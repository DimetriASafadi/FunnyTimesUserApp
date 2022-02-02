package com.example.funnytimesuserapp.SplashSection

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.funnytimesuserapp.Adapters.SplashAdapter
import com.example.funnytimesuserapp.AuthSection.SignInScreen
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.CommonSection.Constants.KeyOpenBefore
import com.example.funnytimesuserapp.databinding.FtSplashMenuBinding
import java.util.*

class SplashMenu : FragmentActivity() {

    val commonFuncs = CommonFuncs()
    lateinit var binding :FtSplashMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (commonFuncs.IsInSP(this, Constants.KeyAppLanguage)){
            commonFuncs.setLocale2(this,commonFuncs.GetFromSP(this,"AppLang")!!)
        }else{
            commonFuncs.setLocale2(this,"ar")
        }
        binding = FtSplashMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.e("Locale", Locale.getDefault().language.toString())
        binding.SplashsContainer.adapter = SplashAdapter(this)
        binding.SplashsContainer.layoutDirection = View.LAYOUT_DIRECTION_RTL
        commonFuncs.WriteOnSP(this,"EnteredBefore","Yes")
        binding.StartButton.setOnClickListener {
            commonFuncs.WriteOnSP(this,KeyOpenBefore,"Yes")
            startActivity(Intent(this,SignInScreen::class.java))
            finish()
        }
        binding.SplashIndicator.setViewPager(binding.SplashsContainer)
    }



}
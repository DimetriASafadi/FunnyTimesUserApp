package com.example.funnytimesuserapp.MainMenuSection.SettingSection.SettingActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.funnytimesuserapp.databinding.FtSettingContactUsScreenBinding

class ContactUsScreen : AppCompatActivity() {

    lateinit var binding: FtSettingContactUsScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtSettingContactUsScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}
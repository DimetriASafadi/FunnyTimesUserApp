package com.example.funnytimesuserapp.MainMenuSection.SettingSection.SettingActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.databinding.FtSettingPolicyPrivacyScreenBinding

class PolicyPrivacyScreen : AppCompatActivity() {

    lateinit var binding: FtSettingPolicyPrivacyScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtSettingPolicyPrivacyScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



    }
}
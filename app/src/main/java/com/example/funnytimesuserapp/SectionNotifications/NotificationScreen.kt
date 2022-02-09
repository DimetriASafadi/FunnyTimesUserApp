package com.example.funnytimesuserapp.SectionNotifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.databinding.FtNotificationScreenBinding

class NotificationScreen : AppCompatActivity() {

    lateinit var binding: FtNotificationScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtNotificationScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



    }
}
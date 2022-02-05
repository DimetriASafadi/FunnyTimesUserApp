package com.example.funnytimesuserapp.SectionService

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.funnytimesuserapp.databinding.FtScreenChaletBinding

class ChaletScreen : AppCompatActivity() {

    lateinit var binding: FtScreenChaletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenChaletBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}
package com.example.funnytimesuserapp.SectionService

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.funnytimesuserapp.databinding.FtScreenClinicBinding

class ClinicScreen : AppCompatActivity() {

    lateinit var binding: FtScreenClinicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenClinicBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}
package com.example.funnytimesuserapp.AuthSection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.MainMenu
import com.example.funnytimesuserapp.databinding.FtCodeConfirmScreenBinding

class CodeConfirmScreen : AppCompatActivity() {

    lateinit var binding : FtCodeConfirmScreenBinding
    val commonFuncs = CommonFuncs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCodeConfirmScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.CheckCode.setOnClickListener {
            startActivity(Intent(this,MainMenu::class.java))
        }



    }
}
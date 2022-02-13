package com.example.funnytimesuserapp.SectionCart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.funnytimesuserapp.databinding.FtCartScreenBinding

class CartScreen : AppCompatActivity() {

    lateinit var binding: FtCartScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCartScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.backBtn.setOnClickListener {
            finish()
        }



//        CartRecycler
//        CartTotal
//        PayNow





    }


}
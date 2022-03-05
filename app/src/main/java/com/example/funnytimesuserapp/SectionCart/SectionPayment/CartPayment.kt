package com.example.funnytimesuserapp.SectionCart.SectionPayment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.databinding.FtScreenCartPaymentBinding

class CartPayment : AppCompatActivity() {

    lateinit var binding: FtScreenCartPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenCartPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val ItemsArr = intent.getSerializableExtra("SelectCartItems")
        Log.e("ItemsArr",ItemsArr.toString())

    }
}
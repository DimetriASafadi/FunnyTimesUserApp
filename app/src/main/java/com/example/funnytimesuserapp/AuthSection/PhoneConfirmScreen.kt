package com.example.funnytimesuserapp.AuthSection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.funnytimesuserapp.SpinnerAdapters.SPhoneAuthCountryAdapter
import com.example.funnytimesuserapp.databinding.FtPhoneConfirmScreenBinding

class PhoneConfirmScreen : AppCompatActivity() {


    lateinit var binding : FtPhoneConfirmScreenBinding
    val CountriesArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtPhoneConfirmScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        CountriesArray.add("+966")
        CountriesArray.add("+962")

        binding.PhoCountryCodesSpin.adapter = SPhoneAuthCountryAdapter(this, CountriesArray)
        binding.PhoCountryCodesSpin.onItemSelectedListener
        binding.PhoCountryCodesSpin?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                binding.PhoCountryCode.text = CountriesHash.get(PhoCountryCodesSpin.selectedItem.toString())?.countryphoneCode
            }
        }
    }
}
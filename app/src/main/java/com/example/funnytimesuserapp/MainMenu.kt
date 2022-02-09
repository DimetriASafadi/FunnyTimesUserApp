package com.example.funnytimesuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.MainMenuSection.CategorySection.FragCategory
import com.example.funnytimesuserapp.MainMenuSection.FavouriteSection.FragFavourite
import com.example.funnytimesuserapp.MainMenuSection.HomeSection.FragHome
import com.example.funnytimesuserapp.MainMenuSection.SettingSection.FragSetting
import com.example.funnytimesuserapp.MainMenuSection.UserSection.FragUser
import com.example.funnytimesuserapp.databinding.FtMainMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMenu : AppCompatActivity() {

    lateinit var binding: FtMainMenuBinding
    val commonFuncs = CommonFuncs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtMainMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val fragment1: Fragment = FragHome()
        val fragment2: Fragment = FragCategory()
        val fragment3: Fragment = FragFavourite()
        val fragment4: Fragment = FragUser()
        val fragment5: Fragment = FragSetting()
        var active = fragment1
        binding.navView

        supportFragmentManager.beginTransaction().add(R.id.main_menu_host_frag, fragment5, "5").hide(
            fragment5
        ).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_menu_host_frag, fragment4, "4").hide(
            fragment4
        ).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_menu_host_frag, fragment3, "3").hide(
            fragment3
        ).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_menu_host_frag, fragment2, "2").hide(
            fragment2
        ).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_menu_host_frag, fragment1, "1").commit()

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction().hide(active).show(fragment1).commit()
                    active = fragment1
                    binding.TopSection.visibility = View.VISIBLE
                    binding.SearchSection.visibility = View.VISIBLE
                    return@setOnItemSelectedListener true
                }
                R.id.nav_category -> {
                    supportFragmentManager.beginTransaction().hide(active).show(fragment2).commit()
                    active = fragment2
                    binding.TopSection.visibility = View.VISIBLE
                    binding.SearchSection.visibility = View.VISIBLE
                    return@setOnItemSelectedListener true
                }
                R.id.nav_favourite -> {
                    supportFragmentManager.beginTransaction().hide(active).show(fragment3).detach(fragment3).attach(fragment3).commit()
                    active = fragment3
                    binding.TopSection.visibility = View.VISIBLE
                    binding.SearchSection.visibility = View.VISIBLE
                    return@setOnItemSelectedListener true
                }
                R.id.nav_user -> {
                    supportFragmentManager.beginTransaction().hide(active).show(fragment4).commit()
                    active = fragment4
                    binding.TopSection.visibility = View.VISIBLE
                    binding.SearchSection.visibility = View.GONE
                    return@setOnItemSelectedListener true

                }
                R.id.nav_setting -> {
                    supportFragmentManager.beginTransaction().hide(active).show(fragment5).commit()
                    active = fragment5
                    binding.TopSection.visibility = View.VISIBLE
                    binding.SearchSection.visibility = View.GONE
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        binding.SearchFilter.setOnClickListener {
            commonFuncs.showFilterDialog(this)
        }


    }

    override fun onResume() {
        super.onResume()
        if (commonFuncs.IsInSP(this, Constants.KeyAppLanguage)){
            commonFuncs.setLocale2(this,commonFuncs.GetFromSP(this, Constants.KeyAppLanguage)!!)
        }else{
            commonFuncs.setLocale2(this,"ar")
        }
    }
}
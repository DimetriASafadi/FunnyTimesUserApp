package com.example.funnytimesuserapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.CommonSection.Constants.KeyUserToken
import com.example.funnytimesuserapp.MainMenuSection.CategorySection.FragCategory
import com.example.funnytimesuserapp.MainMenuSection.FavouriteSection.FragFavourite
import com.example.funnytimesuserapp.MainMenuSection.HomeSection.FragHome
import com.example.funnytimesuserapp.MainMenuSection.SettingSection.FragSetting
import com.example.funnytimesuserapp.MainMenuSection.UserSection.FragUser
import com.example.funnytimesuserapp.SectionCart.CartScreen
import com.example.funnytimesuserapp.SectionSearch.SearchFuncs
import com.example.funnytimesuserapp.SectionSearch.SearchScreen
import com.example.funnytimesuserapp.SectionService.SectionPayment.ServiceDatingScreen
import com.example.funnytimesuserapp.databinding.FtMainMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMenu : AppCompatActivity() {

    lateinit var binding: FtMainMenuBinding
    val commonFuncs = CommonFuncs()
    val searchFuncs = SearchFuncs()

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
                    if (commonFuncs.GetFromSP(this,"FavoriteChanged") == "Yes"){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            supportFragmentManager.beginTransaction().detach(fragment1).commitNow()
                            supportFragmentManager.beginTransaction().attach(fragment1).commitNow()
                            supportFragmentManager.beginTransaction().hide(active).show(fragment1).commitNow()
                        } else {
                            supportFragmentManager.beginTransaction().hide(active).show(fragment1).detach(fragment1).attach(fragment1).commit()
                        }
                        binding.TopSection.visibility = View.VISIBLE
                        binding.SearchSection.visibility = View.VISIBLE
                        active = fragment1
                        commonFuncs.DeleteFromSP(this,"FavoriteChanged")
                        return@setOnItemSelectedListener true
                    }else{
                        supportFragmentManager.beginTransaction().hide(active).show(fragment1).commit()
                        active = fragment1
                        binding.TopSection.visibility = View.VISIBLE
                        binding.SearchSection.visibility = View.VISIBLE
                        return@setOnItemSelectedListener true
                    }
                }
                R.id.nav_category -> {
                    supportFragmentManager.beginTransaction().hide(active).show(fragment2).commit()
                    active = fragment2
                    binding.TopSection.visibility = View.VISIBLE
                    binding.SearchSection.visibility = View.VISIBLE
                    return@setOnItemSelectedListener true
                }
                R.id.nav_favourite -> {
                    if (commonFuncs.IsInSP(this, Constants.KeyUserToken)){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            supportFragmentManager.beginTransaction().detach(fragment3).commitNow()
                            supportFragmentManager.beginTransaction().attach(fragment3).commitNow()
                            supportFragmentManager.beginTransaction().hide(active).show(fragment3).commitNow()
                        } else {
                            supportFragmentManager.beginTransaction().hide(active).show(fragment3).detach(fragment3).attach(fragment3).commit()
                        }
                        supportFragmentManager.beginTransaction().hide(active).show(fragment3).detach(fragment3).attach(fragment3).commit()
                        active = fragment3
                        binding.TopSection.visibility = View.VISIBLE
                        binding.SearchSection.visibility = View.VISIBLE
                        return@setOnItemSelectedListener true
                    }else{
                        commonFuncs.showLoginDialog(this)
                        return@setOnItemSelectedListener false
                    }
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
            searchFuncs.showFilterDialog(this)
        }

        binding.HomeSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val intent = Intent(this, SearchScreen::class.java)
                intent.putExtra("city_id","")
                intent.putExtra("address","")
                intent.putExtra("name",binding.HomeSearch.text.toString())
                intent.putExtra("price[from]","")
                intent.putExtra("price[to]","")
                intent.putExtra("category_id","")
                intent.putExtra("sub_category_id","")
                startActivity(intent)
                return@OnEditorActionListener true
            }
            false
        })

        binding.OpenCart.setOnClickListener {
            startActivity(Intent(this,CartScreen::class.java))
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
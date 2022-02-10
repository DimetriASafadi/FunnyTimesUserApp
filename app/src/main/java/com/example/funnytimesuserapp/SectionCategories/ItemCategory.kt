package com.example.funnytimesuserapp.SectionCategories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTSubCategory
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.CategoriesRecView
import com.example.funnytimesuserapp.RecViews.ItemBigVerticalRecView
import com.example.funnytimesuserapp.RecViews.ItemInsiderRecView
import com.example.funnytimesuserapp.RecViews.ServiceInsiderRecView
import com.example.funnytimesuserapp.databinding.FtCategoryItemBinding

class ItemCategory : AppCompatActivity() {

    lateinit var binding: FtCategoryItemBinding
    val ftCategories = ArrayList<FTCategory>()
    val mostdemanded = ArrayList<FTItem>()
    val mostcommon = ArrayList<FTItem>()
    val mostrecent = ArrayList<FTItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCategoryItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val selfCat = intent.getSerializableExtra("CategoryObj") as FTCategory
        binding.backBtn.setOnClickListener {
            finish()
        }





        val categoriesRecView = CategoriesRecView(ftCategories,this)
        binding.ItmCategoryRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.ItmCategoryRecycler.adapter = categoriesRecView

        val itemBigVerticalRecView = ItemBigVerticalRecView(mostdemanded,this)
        binding.MostDemanded.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostDemanded.adapter = itemBigVerticalRecView

        val itemInsiderRecView = ItemInsiderRecView(mostcommon,this)
        binding.MostCommon.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostCommon.adapter = itemInsiderRecView

        val itemInsiderRecView2 = ItemInsiderRecView(mostrecent,this)
        binding.MostRecent.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostRecent.adapter = itemInsiderRecView2


    }
}
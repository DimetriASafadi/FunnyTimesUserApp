package com.example.funnytimesuserapp.SectionCategories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTService
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.CategoriesRecView
import com.example.funnytimesuserapp.RecViews.ServiceInsiderRecView
import com.example.funnytimesuserapp.RecViews.ServicesBigVerticalRecView
import com.example.funnytimesuserapp.databinding.FtCategoryServiceBinding

class ServiceCategory : AppCompatActivity() {

    lateinit var binding: FtCategoryServiceBinding
    val ftCategories = ArrayList<FTCategory>()
    val mostvisited = ArrayList<FTItem>()
    val mostnearby = ArrayList<FTItem>()
    val mostrecent = ArrayList<FTItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCategoryServiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val selfCat = intent.getSerializableExtra("CategoryObj") as FTCategory

        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.SerCategoryName.text = selfCat.CategoryName







        val categoriesRecView = CategoriesRecView(ftCategories,this)
        binding.SerCategoryRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.SerCategoryRecycler.adapter = categoriesRecView

        val servicesBigVerticalRecView = ServicesBigVerticalRecView(mostvisited,this)
        binding.MostVisited.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostVisited.adapter = servicesBigVerticalRecView

        val serviceInsiderRecView = ServiceInsiderRecView(mostnearby,this)
        binding.MostNearby.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostNearby.adapter = serviceInsiderRecView

        val serviceInsiderRecView2 = ServiceInsiderRecView(mostrecent,this)
        binding.MostRecent.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.MostRecent.adapter = serviceInsiderRecView2



    }
}
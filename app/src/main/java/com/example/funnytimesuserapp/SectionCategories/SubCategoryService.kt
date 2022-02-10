package com.example.funnytimesuserapp.SectionCategories

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTService
import com.example.funnytimesuserapp.Models.FTSubCategory
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.ServiceFullHorizontalRecView
import com.example.funnytimesuserapp.RecViews.ServiceInsider2RecView
import com.example.funnytimesuserapp.RecViews.SubCategoriesRecView
import com.example.funnytimesuserapp.databinding.FtCategorySubServiceBinding

class SubCategoryService : AppCompatActivity() {

    lateinit var binding: FtCategorySubServiceBinding
    val suncategories = ArrayList<FTSubCategory>()
    val servicesarr = ArrayList<FTItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCategorySubServiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val selfCat = intent.getSerializableExtra("CategoryObj") as FTCategory


        binding.SerSubCategoryName.text = selfCat.CategoryName



        val subCategoriesRecView = SubCategoriesRecView(suncategories,this)
        binding.SerCategoryRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.SerCategoryRecycler.adapter = subCategoriesRecView






        val serviceFullHorizontalRecView = ServiceFullHorizontalRecView(servicesarr,this)
        val serviceInsiderRecView = ServiceInsider2RecView(servicesarr,this)
        binding.ServicesRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)
        binding.ServicesRecycler.adapter = serviceFullHorizontalRecView

        var viewmode = 0

        binding.ShowingLargeSmall.setOnClickListener {
            if (viewmode == 1){
                viewmode = 0

                binding.ShowingLargeSmall.setImageResource(R.drawable.ft_dual_duo_view_icon)

                binding.ServicesRecycler.layoutManager = LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL,
                    false)
                binding.ServicesRecycler.adapter = serviceFullHorizontalRecView

            }else{
                viewmode = 1

                binding.ShowingLargeSmall.setImageResource(R.drawable.ft_vertical_view_icon)

                binding.ServicesRecycler.layoutManager = GridLayoutManager(this,2)
                binding.ServicesRecycler.adapter = serviceInsiderRecView
            }
        }
    }
}
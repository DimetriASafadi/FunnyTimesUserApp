package com.example.funnytimesuserapp.SectionCategories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTSubCategory
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.ItemInsider2RecView
import com.example.funnytimesuserapp.RecViews.SubCategoriesRecView
import com.example.funnytimesuserapp.databinding.FtCategorySubItemBinding

class SubCategoryItem : AppCompatActivity() {

    lateinit var binding: FtCategorySubItemBinding
    val suncategories = ArrayList<FTSubCategory>()
    val itemssarr = ArrayList<FTItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCategorySubItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        suncategories.add(FTSubCategory(0,"الكل", R.drawable.ft_cat_all))
        suncategories.add(FTSubCategory(1,"أماكن ترفيهية", R.drawable.ft_cat_places))
        suncategories.add(FTSubCategory(2,"العيادات", R.drawable.ft_cat_clinic))
        suncategories.add(FTSubCategory(3,"الوجبات", R.drawable.ft_cat_food))
        suncategories.add(FTSubCategory(4,"التسوق", R.drawable.ft_cat_shop))





        val subCategoriesRecView = SubCategoriesRecView(suncategories,this)
        binding.ItemCategoryRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.ItemCategoryRecycler.adapter = subCategoriesRecView



        val itemInsider2RecView = ItemInsider2RecView(itemssarr,this)
        binding.ItemsSubCatRecycler.layoutManager = GridLayoutManager(this,2)
        binding.ItemsSubCatRecycler.adapter = itemInsider2RecView

    }
}
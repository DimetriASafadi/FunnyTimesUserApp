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

        itemssarr.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        itemssarr.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))
        itemssarr.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        itemssarr.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))
        itemssarr.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        itemssarr.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))
        itemssarr.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        itemssarr.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))
        itemssarr.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        itemssarr.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))
        itemssarr.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        itemssarr.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))




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
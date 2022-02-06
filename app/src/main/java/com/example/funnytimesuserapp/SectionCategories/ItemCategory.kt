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

        ftCategories.add(FTCategory(0,"الكل", R.drawable.ft_cat_all,2))
        ftCategories.add(FTCategory(1,"أماكن ترفيهية", R.drawable.ft_cat_places,2))
        ftCategories.add(FTCategory(2,"العيادات", R.drawable.ft_cat_clinic,2))
        ftCategories.add(FTCategory(3,"الوجبات", R.drawable.ft_cat_food,2))
        ftCategories.add(FTCategory(4,"التسوق", R.drawable.ft_cat_shop,2))


        mostdemanded.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        mostdemanded.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))
        mostdemanded.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        mostdemanded.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))


        mostcommon.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        mostcommon.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))
        mostcommon.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        mostcommon.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))


        mostrecent.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        mostrecent.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))
        mostrecent.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        mostrecent.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))




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
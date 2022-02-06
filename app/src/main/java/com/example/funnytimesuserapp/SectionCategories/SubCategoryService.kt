package com.example.funnytimesuserapp.SectionCategories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTService
import com.example.funnytimesuserapp.Models.FTSubCategory
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.ServiceInsider2RecView
import com.example.funnytimesuserapp.RecViews.SubCategoriesRecView
import com.example.funnytimesuserapp.databinding.FtCategorySubServiceBinding

class SubCategoryService : AppCompatActivity() {

    lateinit var binding: FtCategorySubServiceBinding
    val suncategories = ArrayList<FTSubCategory>()
    val servicesarr = ArrayList<FTService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCategorySubServiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val selfCat = intent.getSerializableExtra("CategoryObj") as FTCategory


        binding.SerSubCategoryName.text = selfCat.CategoryName

        suncategories.add(FTSubCategory(0,"الكل", R.drawable.ft_cat_all))
        suncategories.add(FTSubCategory(1,"أماكن ترفيهية", R.drawable.ft_cat_places))
        suncategories.add(FTSubCategory(2,"العيادات", R.drawable.ft_cat_clinic))
        suncategories.add(FTSubCategory(3,"الوجبات", R.drawable.ft_cat_food))
        suncategories.add(FTSubCategory(4,"التسوق", R.drawable.ft_cat_shop))



        servicesarr.add(FTService(0,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        servicesarr.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))
        servicesarr.add(FTService(3,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        servicesarr.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))
        servicesarr.add(FTService(0,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        servicesarr.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))
        servicesarr.add(FTService(3,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        servicesarr.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))
        servicesarr.add(FTService(0,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        servicesarr.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))
        servicesarr.add(FTService(3,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        servicesarr.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))


        val subCategoriesRecView = SubCategoriesRecView(suncategories,this)
        binding.SerCategoryRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.SerCategoryRecycler.adapter = subCategoriesRecView



        val serviceInsiderRecView = ServiceInsider2RecView(servicesarr,this)
        binding.ServicesRecycler.layoutManager = GridLayoutManager(this,2)
        binding.ServicesRecycler.adapter = serviceInsiderRecView






    }
}
package com.example.funnytimesuserapp.SectionCategories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTService
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.CategoriesRecView
import com.example.funnytimesuserapp.RecViews.ServiceInsiderRecView
import com.example.funnytimesuserapp.RecViews.ServicesBigVerticalRecView
import com.example.funnytimesuserapp.databinding.FtCategoryServiceBinding

class ServiceCategory : AppCompatActivity() {

    lateinit var binding: FtCategoryServiceBinding
    val ftCategories = ArrayList<FTCategory>()
    val mostvisited = ArrayList<FTService>()
    val mostnearby = ArrayList<FTService>()
    val mostrecent = ArrayList<FTService>()

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

        ftCategories.add(FTCategory(0,"الكل", R.drawable.ft_cat_all,2))
        ftCategories.add(FTCategory(1,"أماكن ترفيهية", R.drawable.ft_cat_places,2))
        ftCategories.add(FTCategory(2,"العيادات", R.drawable.ft_cat_clinic,2))
        ftCategories.add(FTCategory(3,"الوجبات", R.drawable.ft_cat_food,2))
        ftCategories.add(FTCategory(4,"التسوق", R.drawable.ft_cat_shop,2))

        mostvisited.add(FTService(0,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        mostvisited.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))
        mostvisited.add(FTService(3,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        mostvisited.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))


        mostnearby.add(FTService(0,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        mostnearby.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))
        mostnearby.add(FTService(3,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        mostnearby.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))


        mostrecent.add(FTService(0,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        mostrecent.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))
        mostrecent.add(FTService(3,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        mostrecent.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))




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
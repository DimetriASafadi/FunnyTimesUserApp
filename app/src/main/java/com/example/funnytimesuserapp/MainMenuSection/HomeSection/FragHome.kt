package com.example.funnytimesuserapp.MainMenuSection.HomeSection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.funnytimesuserapp.Models.FTBanner
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTService
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.*
import com.example.funnytimesuserapp.databinding.FtMainHomeBinding


class FragHome : Fragment() {
    private var _binding: FtMainHomeBinding? = null
    private val binding get() = _binding!!
    val ftBanners = ArrayList<FTBanner>()
    val ftCategories = ArrayList<FTCategory>()
    val ftMostRented = ArrayList<FTService>()
    val ftMostShoped = ArrayList<FTItem>()
    val ftMostDemanded = ArrayList<FTItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FtMainHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        ftCategories.add(FTCategory(0,"الكل", R.drawable.ft_cat_all))
        ftCategories.add(FTCategory(1,"أماكن ترفيهية", R.drawable.ft_cat_places))
        ftCategories.add(FTCategory(2,"العيادات", R.drawable.ft_cat_clinic))
        ftCategories.add(FTCategory(3,"الوجبات", R.drawable.ft_cat_food))
        ftCategories.add(FTCategory(4,"التسوق", R.drawable.ft_cat_shop))

        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))

        ftMostRented.add(FTService(0,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        ftMostRented.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))
        ftMostRented.add(FTService(3,"شاليه أخضر مع ملعب طائرة","https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg",true,"حي الرياض,الرياض",4.5,50))
        ftMostRented.add(FTService(1,"عيادة الأسنان التخصصية","https://static.bookinghealth.com/uploads/clinics/gallery/m/4498-1408-otr_Depositphotos_316360964_xl-2015-1.jpg",true,"حي الرياض,الرياض",4.5,50))

        ftMostShoped.add(FTItem(1,"ساعة فاخرة نخب أول","https://cdn11.bigcommerce.com/s-pkla4xn3/images/stencil/1280x1280/products/25392/233013/2015-Curren-Men-Luxury-Brand-Sport-Watches-Water-Quartz-Hours-Date-Hand-Clock-Men-Full-Stainless__34395.1549603718.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",300))
        ftMostShoped.add(FTItem(2,"بولو ماركة أصلية","https://cache.mrporter.com/variants/images/3633577411310822/in/w2000_q60.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",20))


        ftMostDemanded.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        ftMostDemanded.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))
        ftMostDemanded.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        ftMostDemanded.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))
        ftMostDemanded.add(FTItem(1,"بيتزا مارجريتا بالجبنة","https://natashaskitchen.com/wp-content/uploads/2019/04/Best-Burger-5-600x900.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",30))
        ftMostDemanded.add(FTItem(2,"برجر بيف حار جداً","https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",true,"حي الرياض,الرياض",4.5,50,"متجر ماركة",15))

        val bannerRecView = BannerRecView(ftBanners,requireContext())
        binding.HomeAdBannersRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.HomeAdBannersRecycler.adapter = bannerRecView
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.HomeAdBannersRecycler)
        binding.BannerIndicator.attachToRecyclerView(binding.HomeAdBannersRecycler,pagerSnapHelper)

        val categoriesRecView = CategoriesRecView(ftCategories,requireContext())
        binding.HomeCategoriesRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.HomeCategoriesRecycler.adapter = categoriesRecView


        val servicesBigVerticalRecView = ServicesBigVerticalRecView(ftMostRented,requireContext())
        binding.HomeServedRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.HomeServedRecycler.adapter = servicesBigVerticalRecView
        val pagerSnapHelper2 = PagerSnapHelper()
        pagerSnapHelper2.attachToRecyclerView(binding.HomeServedRecycler)


        val itemNormalHorizontalRecView = ItemNormalHorizontalRecView(ftMostShoped,requireContext())
        binding.HomePurchasedRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,
            false)
        binding.HomePurchasedRecycler.adapter = itemNormalHorizontalRecView


        val itemInsiderRecView = ItemInsiderRecView(ftMostDemanded,requireContext())
        binding.HomeDemandedRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.HomeDemandedRecycler.adapter = itemInsiderRecView
        val pagerSnapHelper3 = PagerSnapHelper()
        pagerSnapHelper3.attachToRecyclerView(binding.HomeDemandedRecycler)



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.funnytimesuserapp.MainMenuSection.HomeSection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.funnytimesuserapp.Models.FTBanner
import com.example.funnytimesuserapp.RecViews.BannerRecView
import com.example.funnytimesuserapp.databinding.FtMainHomeBinding


class FragHome : Fragment() {
    private var _binding: FtMainHomeBinding? = null
    private val binding get() = _binding!!
    val ftBanners = ArrayList<FTBanner>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FtMainHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        ftBanners.add(FTBanner(0,"https://vcdn.bergfex.at/images/resized/f4/e91963ae84ef2af4_9c3f0a64cd4ad526@2x.jpg"))
        val bannerRecView = BannerRecView(ftBanners,requireContext())
        binding.HomeAdBannersRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.HomeAdBannersRecycler.adapter = bannerRecView
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.HomeAdBannersRecycler)
        binding.BannerIndicator.attachToRecyclerView(binding.HomeAdBannersRecycler,pagerSnapHelper)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
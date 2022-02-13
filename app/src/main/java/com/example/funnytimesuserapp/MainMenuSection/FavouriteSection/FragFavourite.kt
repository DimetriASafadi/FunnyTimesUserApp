package com.example.funnytimesuserapp.MainMenuSection.FavouriteSection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.RecViews.FavoritesRecView
import com.example.funnytimesuserapp.databinding.FtMainFavouriteBinding


class FragFavourite: Fragment()  {
    private var _binding:FtMainFavouriteBinding? = null
    private val binding get() = _binding!!
    val ftItems = ArrayList<FTItem>()
    lateinit var favoritesRecView : FavoritesRecView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FtMainFavouriteBinding.inflate(inflater, container, false)







        favoritesRecView = FavoritesRecView(ftItems,requireContext())
        binding.FavoriteItemsRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,
            false)
        binding.FavoriteItemsRecycler.adapter = favoritesRecView


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
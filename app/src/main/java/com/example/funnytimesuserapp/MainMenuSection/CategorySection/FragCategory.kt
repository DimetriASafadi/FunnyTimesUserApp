package com.example.funnytimesuserapp.MainMenuSection.CategorySection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.funnytimesuserapp.databinding.FtMainCategoryBinding

class FragCategory : Fragment() {
    private var _binding: FtMainCategoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FtMainCategoryBinding.inflate(inflater, container, false)

        binding




        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
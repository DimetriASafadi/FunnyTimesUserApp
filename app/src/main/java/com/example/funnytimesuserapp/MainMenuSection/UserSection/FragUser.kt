package com.example.funnytimesuserapp.MainMenuSection.UserSection

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.databinding.FtMainUserBinding

class FragUser : Fragment() {
    private var _binding: FtMainUserBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FtMainUserBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.BooksButton.setOnClickListener {
            binding.BooksButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.BooksTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_dark_blue,null))
            binding.OrdersButton.setBackgroundResource(0)
            binding.OrdersTextImg.setTextColor(resources.getColor(R.color.ft_grey_1,null))
            binding.OrdersTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_grey_1,null))
        }
        binding.OrdersButton.setOnClickListener {
            binding.OrdersButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.OrdersTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.OrdersTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_dark_blue,null))
            binding.BooksButton.setBackgroundResource(0)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_grey_1,null))
            binding.BooksTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_grey_1,null))
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
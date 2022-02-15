package com.example.funnytimesuserapp.SectionVendor

import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.ItemInsider2RecView
import com.example.funnytimesuserapp.databinding.FtScreenVendorBinding
import com.jaygoo.widget.RangeSeekBar

class VendorScreen : AppCompatActivity() {

    lateinit var binding: FtScreenVendorBinding
    val vendorItems = ArrayList<FTItem>()

    var minifilterDialog:Dialog? = null
    lateinit var itemInsider2RecView : ItemInsider2RecView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenVendorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        SetUpVendorFilterDialog(this)
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.VendorName.text = "مطعم برجر"


        itemInsider2RecView = ItemInsider2RecView(vendorItems,this)
        binding.VendorItemsRecycler.layoutManager = GridLayoutManager(this,2)
        binding.VendorItemsRecycler.adapter = itemInsider2RecView

        binding.SearchFilter.setOnClickListener {
            if (minifilterDialog != null && !minifilterDialog!!.isShowing){
                minifilterDialog!!.show()
            }
        }
        binding.VendorSearch.setOnClickListener {

        }

    }



    fun SetUpVendorFilterDialog(activity: Activity) {
        minifilterDialog = Dialog(activity)
        minifilterDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        minifilterDialog?.setCancelable(true)
        minifilterDialog?.setContentView(R.layout.ft_dialog_mini_filter)
        val rangeseek = minifilterDialog?.findViewById<RangeSeekBar>(R.id.PriceRangeBar)
        val BarMax = minifilterDialog?.findViewById<TextView>(R.id.BarMax)
        val BarMin = minifilterDialog?.findViewById<TextView>(R.id.BarMin)
        rangeseek?.setOnRangeChangedListener { view, min, max, isFromUser ->
            BarMax?.text = max.toInt().toString()+" ر.س"
            BarMin?.text = min.toInt().toString()+" ر.س"
        }
        val window: Window = minifilterDialog?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(activity.resources
                .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        minifilterDialog?.show()
    }


}
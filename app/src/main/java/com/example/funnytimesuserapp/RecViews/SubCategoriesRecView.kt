package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.Models.FTSubCategory
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.SectionCategories.SubCategoryItem
import com.example.funnytimesuserapp.SectionCategories.SubCategoryService

class SubCategoriesRecView (val data : ArrayList<FTSubCategory>, val context: Context) : RecyclerView.Adapter<SubCatViewHolder>() {

    var selectedItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCatViewHolder {
        return SubCatViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_category, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: SubCatViewHolder, position: Int) {





            if (selectedItem == position){
                holder.WholeCategory.setBackgroundResource(R.drawable.ft_radius_fill_light)
                holder.WholeCategory.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.ft_orange))
                holder.CategoryName.setTextColor(context.getColor(R.color.ft_white))
            }else{
                holder.WholeCategory.setBackgroundResource(R.drawable.ft_radius_stroke)
                holder.WholeCategory.backgroundTintList = null
                holder.CategoryName.setTextColor(context.getColor(R.color.ft_black))
            }
            holder.WholeCategory.setOnClickListener {
                selectedItem = position
                notifyDataSetChanged()
            }







        Glide.with(context)
            .load(data[position].SubCatIcon)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.CategoryImage)
        holder.CategoryName.text = data[position].SubCatName
    }
}
class SubCatViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val WholeCategory = view.findViewById<LinearLayout>(R.id.WholeCategory)
    val CategoryImage = view.findViewById<ImageView>(R.id.CategoryImage)
    val CategoryName = view.findViewById<TextView>(R.id.CategoryName)
}
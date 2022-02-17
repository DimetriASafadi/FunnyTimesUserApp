package com.example.funnytimesuserapp.RecViews

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.Interfaces.SubCategoryClickListener
import com.example.funnytimesuserapp.Models.FTSubCategory
import com.example.funnytimesuserapp.R

class SubCategoriesRecView (val data : ArrayList<FTSubCategory>, val context: Activity,val subCategoryClickListener: SubCategoryClickListener,selectedone:Int) : RecyclerView.Adapter<SubCatViewHolder>() {

    var selectedItem = selectedone

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCatViewHolder {
        return SubCatViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_category, parent, false))
    }

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
                if (position == 0){
                    context.finish()
                }else{
                    selectedItem = position
                    subCategoryClickListener.OnSubCategoryClickListener(data[position])
                    notifyDataSetChanged()
                }
            }


        if (position == 0){
            holder.CategoryImage.setImageResource(R.drawable.ft_cat_all)
        }else{
            Glide.with(context)
                .load(data[position].SubCatIcon)
                .centerCrop()
                .placeholder(R.drawable.ft_broken_image)
                .into(holder.CategoryImage)
        }

        holder.CategoryName.text = data[position].SubCatName

    }
}
class SubCatViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val WholeCategory = view.findViewById<LinearLayout>(R.id.WholeCategory)
    val CategoryImage = view.findViewById<ImageView>(R.id.CategoryImage)
    val CategoryName = view.findViewById<TextView>(R.id.CategoryName)
}
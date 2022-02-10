package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.SectionCategories.ItemCategory
import com.example.funnytimesuserapp.SectionCategories.ServiceCategory
import com.example.funnytimesuserapp.SectionCategories.SubCategoryItem
import com.example.funnytimesuserapp.SectionCategories.SubCategoryService
import com.example.funnytimesuserapp.SectionService.ChaletScreen

class CategoriesRecView(val data : ArrayList<FTCategory>, val context: Context) : RecyclerView.Adapter<CatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_category, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {



//        if (data[position].CategoryLevel == 1){
//            holder.WholeCategory.setOnClickListener {
//                val intent = Intent(context,ItemCategory::class.java)
//                intent.putExtra("CategoryObj",data[position])
//                context.startActivity(intent)
//            }
//        }else{
//            holder.WholeCategory.setOnClickListener {
//                val intent = Intent(context,SubCategoryItem::class.java)
//                intent.putExtra("CategoryObj",data[position])
//                context.startActivity(intent)
//            }
//        }

        Glide.with(context)
            .load(data[position].CategoryIcon)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.CategoryImage)

//        holder.CategoryImage.setImageResource(data[position].CategoryIcon)
        holder.CategoryName.text = data[position].CategoryName
    }
}
class CatViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val WholeCategory = view.findViewById<LinearLayout>(R.id.WholeCategory)
    val CategoryImage = view.findViewById<ImageView>(R.id.CategoryImage)
    val CategoryName = view.findViewById<TextView>(R.id.CategoryName)
}
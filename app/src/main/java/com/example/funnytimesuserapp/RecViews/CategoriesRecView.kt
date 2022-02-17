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
import com.example.funnytimesuserapp.Models.FTCategory
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.SectionCategories.ItemCategory
import com.example.funnytimesuserapp.SectionCategories.ServiceCategory
import com.example.funnytimesuserapp.SectionCategories.SubCategoryItem
import com.example.funnytimesuserapp.SectionCategories.SubCategoryService

class CategoriesRecView(val data : ArrayList<FTCategory>, val context: Context,val catType:String,val catLvl:Int) : RecyclerView.Adapter<CatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_category, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {


        if (position != 0){
            if (data[position].CategoryType == "service"){
                holder.WholeCategory.setOnClickListener {
                    val intent = Intent(context,ServiceCategory::class.java)
                    intent.putExtra("CategoryObj",data[position])
                    context.startActivity(intent)
                }
            } else if (data[position].CategoryType == "product"){
                holder.WholeCategory.setOnClickListener {
                    val intent = Intent(context,ItemCategory::class.java)
                    intent.putExtra("CategoryObj",data[position])
                    context.startActivity(intent)
                }
            }

            if (catLvl == 1){
                if (catType == "service"){
                    holder.WholeCategory.setOnClickListener {
                        val intent = Intent(context,SubCategoryService::class.java)
                        intent.putExtra("subCat",data[position])
                        intent.putExtra("subPos",position)
                        intent.putExtra("subCats",data)
                        context.startActivity(intent)
                    }
                } else if (catType == "product"){
                    holder.WholeCategory.setOnClickListener {
                        val intent = Intent(context,SubCategoryItem::class.java)
                        intent.putExtra("subCat",data[position])
                        intent.putExtra("subPos",position)
                        intent.putExtra("subCats",data)
                        context.startActivity(intent)
                    }
                }
            }



            Glide.with(context)
                .load(data[position].CategoryIcon)
                .centerCrop()
                .placeholder(R.drawable.ft_broken_image)
                .into(holder.CategoryImage)

//        holder.CategoryImage.setImageResource(data[position].CategoryIcon)
            holder.CategoryName.text = data[position].CategoryName
        }else{
            holder.WholeCategory.setBackgroundResource(R.drawable.ft_radius_fill_light)
            holder.WholeCategory.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.ft_orange))
            holder.CategoryName.setTextColor(context.getColor(R.color.ft_white))
            holder.CategoryImage.setImageResource(R.drawable.ft_cat_all)
            holder.CategoryName.text = data[position].CategoryName
        }

    }
}
class CatViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val WholeCategory = view.findViewById<LinearLayout>(R.id.WholeCategory)
    val CategoryImage = view.findViewById<ImageView>(R.id.CategoryImage)
    val CategoryName = view.findViewById<TextView>(R.id.CategoryName)
}
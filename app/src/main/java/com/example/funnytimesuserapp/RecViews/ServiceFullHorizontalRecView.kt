package com.example.funnytimesuserapp.RecViews

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.MainMenuSection.FavouriteSection.FavoriteFuncs
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTService
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.SectionService.ChaletScreen
import com.makeramen.roundedimageview.RoundedImageView
import com.willy.ratingbar.BaseRatingBar

class ServiceFullHorizontalRecView(val data : ArrayList<FTItem>, val context: Activity) : RecyclerView.Adapter<SFHViewHolder>() {

    val commonFuncs = CommonFuncs()
    val favoriteFuncs = FavoriteFuncs()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SFHViewHolder {
        return SFHViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_service_full_horizontal, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: SFHViewHolder, position: Int) {

        if (data[position].ItemIsFavorite!!){
            holder.SFHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
        }else{
            holder.SFHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
        }

        holder.SFHImage.setOnClickListener {
            context.startActivity(Intent(context, ChaletScreen::class.java))
        }
        
        holder.SFHIsFavourite.setOnClickListener {
            if (commonFuncs.IsInSP(context, Constants.KeyUserToken)){
                data[position].ItemIsFavorite = !data[position].ItemIsFavorite
                if (data[position].ItemIsFavorite){
                    holder.SFHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                    favoriteFuncs.add_favourite_Request(context,data[position].ItemId!!)
                }else{
                    holder.SFHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
                    favoriteFuncs.delete_favourite_Request(context,data[position].ItemId!!)
                }
            }else{
                commonFuncs.showLoginDialog(context)
            }
        }

        Glide.with(context)
            .load(data[position].ItemImage)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.SFHImage)
        holder.SFHTitle.text = data[position].ItemName
        holder.SFHRating.rating = data[position].ItemRating!!.toFloat()
        holder.SFHRating.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }
        holder.SFHRatingText.text = data[position].ItemRatingText.toString()

    }
}

class SFHViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val SFHImage = view.findViewById<RoundedImageView>(R.id.SFHImage)
    val SFHIsFavourite = view.findViewById<RelativeLayout>(R.id.SFHIsFavourite)
    val SFHFavoriteIcon = view.findViewById<ImageView>(R.id.SFHFavoriteIcon)
    val SFHTitle = view.findViewById<TextView>(R.id.SFHTitle)
    val SFHRating = view.findViewById<BaseRatingBar>(R.id.SFHRating)
    val SFHRatingText = view.findViewById<TextView>(R.id.SFHRatingText)
}
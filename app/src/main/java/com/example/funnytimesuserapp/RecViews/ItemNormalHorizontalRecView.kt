package com.example.funnytimesuserapp.RecViews

import android.app.Activity
import android.content.Context
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
import com.example.funnytimesuserapp.R
import com.makeramen.roundedimageview.RoundedImageView
import com.willy.ratingbar.BaseRatingBar

class ItemNormalHorizontalRecView (val data : ArrayList<FTItem>, val context: Activity) : RecyclerView.Adapter<INHViewHolder>() {

    val commonFuncs = CommonFuncs()
    val favoriteFuncs = FavoriteFuncs()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): INHViewHolder {
        return INHViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_normal_horizontal, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: INHViewHolder, position: Int) {

        if (data[position].ItemIsFavorite){
            holder.NHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
        }else{
            holder.NHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
        }


        holder.NHIsFavorite.setOnClickListener {
            if (commonFuncs.IsInSP(context, Constants.KeyUserToken)){
                data[position].ItemIsFavorite = !data[position].ItemIsFavorite
                if (data[position].ItemIsFavorite){
                    holder.NHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                    favoriteFuncs.add_favourite_Request(context,data[position].ItemId!!)
                }else{
                    holder.NHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
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
            .into(holder.NHImage)
        holder.NHTitle.text = data[position].ItemName
        holder.NHLocation.text = data[position].ItemLocation
        holder.NHRating.rating = data[position].ItemRating!!.toFloat()
        holder.NHRating.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }
        holder.NHRatingText.text = data[position].ItemRatingText.toString()
        holder.NHPrice.text = data[position].ItemPrice.toString() + "ر.س"
        holder.NHShopName.text = data[position].ItemShop.toString()

    }
}

class INHViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val NHPrice = view.findViewById<TextView>(R.id.NHPrice)
    val NHImage = view.findViewById<RoundedImageView>(R.id.NHImage)
    val NHTitle = view.findViewById<TextView>(R.id.NHTitle)
    val NHShopName = view.findViewById<TextView>(R.id.NHShopName)
    val NHLocation = view.findViewById<TextView>(R.id.NHLocation)
    val NHRating = view.findViewById<BaseRatingBar>(R.id.NHRating)
    val NHRatingText = view.findViewById<TextView>(R.id.NHRatingText)
    val NHFavoriteIcon = view.findViewById<ImageView>(R.id.NHFavoriteIcon)
    val NHIsFavorite = view.findViewById<RelativeLayout>(R.id.NHIsFavorite)
}
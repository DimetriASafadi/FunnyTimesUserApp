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
import com.example.funnytimesuserapp.CommonSection.Constants.KeyUserToken
import com.example.funnytimesuserapp.MainMenuSection.FavouriteSection.FavoriteFuncs
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.R
import com.makeramen.roundedimageview.RoundedImageView
import com.willy.ratingbar.BaseRatingBar

class FavoritesRecView(val data : ArrayList<FTItem>, val context: Activity) : RecyclerView.Adapter<FFNHViewHolder>() {

    val commonFuncs = CommonFuncs()
    val favoriteFuncs = FavoriteFuncs()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FFNHViewHolder {
        return FFNHViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_favorite, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: FFNHViewHolder, position: Int) {

        if (data[position].ItemIsFavorite){
            holder.FNHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
        }else{
            holder.FNHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
        }

        holder.FNHIsFavorite.setOnClickListener {
            if (commonFuncs.IsInSP(context,KeyUserToken)){
                data[position].ItemIsFavorite = !data[position].ItemIsFavorite
                if (data[position].ItemIsFavorite){
                    holder.FNHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                    favoriteFuncs.add_favourite_Request(context,data[position].ItemId!!)
                }else{
                    holder.FNHFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
                    favoriteFuncs.delete_favourite_Request(context,data[position].ItemId!!)
                    removeitem(position)
                }
            }else{
                commonFuncs.showLoginDialog(context)
            }
        }

        Glide.with(context)
            .load(data[position].ItemImage)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.FNHImage)
        holder.FNHTitle.text = data[position].ItemName
        holder.FNHLocation.text = data[position].ItemLocation
        holder.FNHRating.rating = data[position].ItemRating!!.toFloat()
        holder.FNHRating.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }
        holder.FNHRatingText.text = data[position].ItemRatingText.toString()
        holder.FNHPrice.text = data[position].ItemPrice.toString() + "ر.س"
        holder.FNHShopName.text = data[position].ItemShop.toString()

    }



    fun removeitem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }



}

class FFNHViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val FNHPrice = view.findViewById<TextView>(R.id.FNHPrice)
    val FNHImage = view.findViewById<RoundedImageView>(R.id.FNHImage)
    val FNHTitle = view.findViewById<TextView>(R.id.FNHTitle)
    val FNHShopName = view.findViewById<TextView>(R.id.FNHShopName)
    val FNHLocation = view.findViewById<TextView>(R.id.FNHLocation)
    val FNHRating = view.findViewById<BaseRatingBar>(R.id.FNHRating)
    val FNHRatingText = view.findViewById<TextView>(R.id.FNHRatingText)
    val FNHFavoriteIcon = view.findViewById<ImageView>(R.id.FNHFavoriteIcon)
    val FNHIsFavorite = view.findViewById<RelativeLayout>(R.id.FNHIsFavorite)
}
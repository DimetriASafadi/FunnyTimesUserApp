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
import com.example.funnytimesuserapp.SectionItems.FoodScreen
import com.example.funnytimesuserapp.SectionItems.ProductScreen
import com.example.funnytimesuserapp.SectionService.ChaletScreen
import com.example.funnytimesuserapp.SectionService.ClinicScreen
import com.makeramen.roundedimageview.RoundedImageView
import com.willy.ratingbar.BaseRatingBar

class ServiceInsiderRecView (val data : ArrayList<FTItem>, val context: Activity) : RecyclerView.Adapter<SIViewHolder>() {

    val commonFuncs = CommonFuncs()
    val favoriteFuncs = FavoriteFuncs()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SIViewHolder {
        return SIViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_service_insider, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: SIViewHolder, position: Int) {

        if (data[position].ItemIsFavorite!!){
            holder.SIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
        }else{
            holder.SIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
        }

        holder.SIImage.setOnClickListener {
            if (data[position].ItemType == "booking"){
                val into = Intent(context,ChaletScreen::class.java)
                into.putExtra("ItemId",data[position].ItemId)
                context.startActivity(into)
            }else if (data[position].ItemType == "service"){
                val into = Intent(context, ClinicScreen::class.java)
                into.putExtra("ItemId",data[position].ItemId)
                context.startActivity(into)
            }else if (data[position].ItemType == "food"){
                val into = Intent(context, FoodScreen::class.java)
                into.putExtra("ItemId",data[position].ItemId)
                context.startActivity(into)
            }else if (data[position].ItemType == "shop"){
                val into = Intent(context, ProductScreen::class.java)
                into.putExtra("ItemId",data[position].ItemId)
                context.startActivity(into)
            }
        }

        holder.SIIsFavourite.setOnClickListener {
            if (commonFuncs.IsInSP(context, Constants.KeyUserToken)){
                data[position].ItemIsFavorite = !data[position].ItemIsFavorite
                if (data[position].ItemIsFavorite){
                    holder.SIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
                    favoriteFuncs.add_favourite_Request(context,data[position].ItemId!!)
                }else{
                    holder.SIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
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
            .into(holder.SIImage)
        holder.SITitle.text = data[position].ItemName
        holder.SIRating.rating = data[position].ItemRating!!.toFloat()
        holder.SIRating.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }
        holder.SIRatingText.text = data[position].ItemRatingText.toString()

    }
}

class SIViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val SIImage = view.findViewById<RoundedImageView>(R.id.SIImage)
    val SIIsFavourite = view.findViewById<RelativeLayout>(R.id.SIIsFavourite)
    val SIFavoriteIcon = view.findViewById<ImageView>(R.id.SIFavoriteIcon)
    val SITitle = view.findViewById<TextView>(R.id.SITitle)
    val SIRating = view.findViewById<BaseRatingBar>(R.id.SIRating)
    val SIRatingText = view.findViewById<TextView>(R.id.SIRatingText)
}
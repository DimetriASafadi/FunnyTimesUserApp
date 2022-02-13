package com.example.funnytimesuserapp.RecViews

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
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.Models.FTService
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.SectionService.ChaletScreen
import com.makeramen.roundedimageview.RoundedImageView
import com.willy.ratingbar.BaseRatingBar

class ServiceInsider2RecView  (val data : ArrayList<FTItem>, val context: Context) : RecyclerView.Adapter<SI2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SI2ViewHolder {
        return SI2ViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_service_insider_twice, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: SI2ViewHolder, position: Int) {
1
        if (data[position].ItemIsFavorite!!){
            holder.SIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
        }else{
            holder.SIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
        }

        holder.SIImage.setOnClickListener {
            context.startActivity(Intent(context, ChaletScreen::class.java))
        }
        holder.SIIsFavourite.setOnClickListener {
            data[position].ItemIsFavorite = !data[position].ItemIsFavorite!!
            if (data[position].ItemIsFavorite!!){
                holder.SIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
            }else{
                holder.SIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
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

class SI2ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val SIImage = view.findViewById<RoundedImageView>(R.id.SIImage)
    val SIIsFavourite = view.findViewById<RelativeLayout>(R.id.SIIsFavourite)
    val SIFavoriteIcon = view.findViewById<ImageView>(R.id.SIFavoriteIcon)
    val SITitle = view.findViewById<TextView>(R.id.SITitle)
    val SIRating = view.findViewById<BaseRatingBar>(R.id.SIRating)
    val SIRatingText = view.findViewById<TextView>(R.id.SIRatingText)
}
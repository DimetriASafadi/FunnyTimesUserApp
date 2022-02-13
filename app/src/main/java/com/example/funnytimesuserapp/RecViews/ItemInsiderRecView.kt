package com.example.funnytimesuserapp.RecViews

import android.content.Context
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
import com.example.funnytimesuserapp.R
import com.makeramen.roundedimageview.RoundedImageView
import com.willy.ratingbar.BaseRatingBar

class ItemInsiderRecView (val data : ArrayList<FTItem>, val context: Context) : RecyclerView.Adapter<IIViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IIViewHolder {
        return IIViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_insider_item, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: IIViewHolder, position: Int) {

        if (data[position].ItemIsFavorite){
            holder.IIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
        }else{
            holder.IIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
        }


        holder.IIsFavourite.setOnClickListener {
            data[position].ItemIsFavorite = !data[position].ItemIsFavorite
            if (data[position].ItemIsFavorite){
                holder.IIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
            }else{
                holder.IIFavoriteIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
            }
        }

        Glide.with(context)
            .load(data[position].ItemImage)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.IImage)
        holder.ITitle.text = data[position].ItemName
        holder.IRating.rating = data[position].ItemRating!!.toFloat()
        holder.IRating.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }

        holder.IRatingText.text = data[position].ItemRatingText.toString()
        holder.IIPrice.text = data[position].ItemPrice.toString() + "ر.س"

    }
}

class IIViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val IIPrice = view.findViewById<TextView>(R.id.IIPrice)
    val IIsFavourite = view.findViewById<RelativeLayout>(R.id.IIsFavourite)
    val IIFavoriteIcon = view.findViewById<ImageView>(R.id.IIFavoriteIcon)
    val IRating = view.findViewById<BaseRatingBar>(R.id.IRating)
    val IRatingText = view.findViewById<TextView>(R.id.IRatingText)
    val ITitle = view.findViewById<TextView>(R.id.ITitle)
    val IImage = view.findViewById<RoundedImageView>(R.id.IImage)

}
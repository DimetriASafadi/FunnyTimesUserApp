package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.R
import com.makeramen.roundedimageview.RoundedImageView
import com.willy.ratingbar.BaseRatingBar
import de.hdodenhof.circleimageview.CircleImageView

class ItemInsider2RecView (val data : ArrayList<FTItem>, val context: Context) : RecyclerView.Adapter<II2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): II2ViewHolder {
        return II2ViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_item_big_vertical, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: II2ViewHolder, position: Int) {

        if (data[position].ItemIsFavorite){
            holder.IIFavIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
        }else{
            holder.IIFavIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
        }


        holder.IIIsFavorite.setOnClickListener {
            data[position].ItemIsFavorite = !data[position].ItemIsFavorite
            if (data[position].ItemIsFavorite){
                holder.IIFavIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
            }else{
                holder.IIFavIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
            }
        }

        Glide.with(context)
            .load(Constants.APIMain +data[position].ItemImage)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.IIImage)
        holder.IITitle.text = data[position].ItemName
        holder.IIRating.rating = data[position].ItemRating.toFloat()
        holder.IIRatingText.text = data[position].ItemRatingText.toString()
        holder.IIPrice.text = data[position].ItemPrice.toString() + "ر.س"

    }
}

class II2ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val IIPrice = view.findViewById<TextView>(R.id.IIPrice)
    val IIIsFavorite = view.findViewById<CircleImageView>(R.id.IIIsFavorite)
    val IIFavIcon = view.findViewById<ImageView>(R.id.IIFavIcon)
    val IIRating = view.findViewById<BaseRatingBar>(R.id.IIRating)
    val IIRatingText = view.findViewById<TextView>(R.id.IIRatingText)
    val IITitle = view.findViewById<TextView>(R.id.IITitle)
    val IIImage = view.findViewById<RoundedImageView>(R.id.IIImage)

}
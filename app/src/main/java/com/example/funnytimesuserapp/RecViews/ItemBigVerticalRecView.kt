package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.SectionService.ChaletScreen
import com.makeramen.roundedimageview.RoundedImageView
import com.willy.ratingbar.BaseRatingBar
import de.hdodenhof.circleimageview.CircleImageView

class ItemBigVerticalRecView (val data : ArrayList<FTItem>, val context: Context) : RecyclerView.Adapter<BViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BViewHolder {
        return BViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_big_vertical, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: BViewHolder, position: Int) {

        if (data[position].ItemIsFavorite){
            holder.BVFavIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
        }else{
            holder.BVFavIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
        }

        holder.BVImage.setOnClickListener {
            context.startActivity(Intent(context, ChaletScreen::class.java))
        }
        holder.BVIsFavorite.setOnClickListener {
            data[position].ItemIsFavorite = !data[position].ItemIsFavorite
            if (data[position].ItemIsFavorite){
                holder.BVFavIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
            }else{
                holder.BVFavIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
            }
        }

        Glide.with(context)
            .load(Constants.APIMain +data[position].ItemImage)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.BVImage)
        holder.BVTitle.text = data[position].ItemName
        holder.BVLocation.text = data[position].ItemLocation
        holder.BVRating.rating = data[position].ItemRating.toFloat()
        holder.BVRatingText.text = data[position].ItemRatingText.toString()

    }
}

class BViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val BVImage = view.findViewById<RoundedImageView>(R.id.BVImage)
    val BVIsFavorite = view.findViewById<CircleImageView>(R.id.BVIsFavorite)
    val BVFavIcon = view.findViewById<CircleImageView>(R.id.BVFavoriteIcon)
    val BVTitle = view.findViewById<TextView>(R.id.BVTitle)
    val BVLocation = view.findViewById<TextView>(R.id.BVLocation)
    val BVRating = view.findViewById<BaseRatingBar>(R.id.BVRating)
    val BVRatingText = view.findViewById<TextView>(R.id.BVRatingText)
}
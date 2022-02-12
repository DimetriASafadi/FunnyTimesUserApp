package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.Models.FTItem
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.SectionService.ChaletScreen
import com.makeramen.roundedimageview.RoundedImageView
import com.willy.ratingbar.BaseRatingBar
import com.willy.ratingbar.BaseRatingBar.OnRatingChangeListener
import de.hdodenhof.circleimageview.CircleImageView

class ServicesBigVerticalRecView (val data : ArrayList<FTItem>, val context: Context) : RecyclerView.Adapter<SBViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SBViewHolder {
        return SBViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_service_big_vertical, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: SBViewHolder, position: Int) {

        if (data[position].ItemIsFavorite!!){
            holder.SBVFavIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
        }else{
            holder.SBVFavIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
        }

        holder.SBVImage.setOnClickListener {
            context.startActivity(Intent(context,ChaletScreen::class.java))
        }
        holder.SBVIsFavorite.setOnClickListener {
            data[position].ItemIsFavorite = !data[position].ItemIsFavorite!!
            if (data[position].ItemIsFavorite!!){
                holder.SBVFavIcon.setImageResource(R.drawable.ft_favorite_heart_like_icon)
            }else{
                holder.SBVFavIcon.setImageResource(R.drawable.ft_favorite_heart_unlike_icon)
            }
        }

        Glide.with(context)
            .load(data[position].ItemImage)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.SBVImage)
        holder.SBVTitle.text = data[position].ItemName
        holder.SBVLocation.text = data[position].ItemLocation
        holder.SBVRating.rating = data[position].ItemRating!!.toFloat()
        holder.SBVRating.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }

        holder.SBVRatingText.text = data[position].ItemRatingText.toString()

    }
}

class SBViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val SBVImage = view.findViewById<RoundedImageView>(R.id.SBVImage)
    val SBVIsFavorite = view.findViewById<CircleImageView>(R.id.SBVIsFavorite)
    val SBVFavIcon = view.findViewById<CircleImageView>(R.id.SBVFavIcon)
    val SBVTitle = view.findViewById<TextView>(R.id.SBVTitle)
    val SBVLocation = view.findViewById<TextView>(R.id.SBVLocation)
    val SBVRating = view.findViewById<BaseRatingBar>(R.id.SBVRating)
    val SBVRatingText = view.findViewById<TextView>(R.id.SBVRatingText)
}
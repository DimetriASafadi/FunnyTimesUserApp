package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.CommonSection.Constants.APIMain
import com.example.funnytimesuserapp.Models.FTBanner
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.SectionService.ChaletScreen
import com.makeramen.roundedimageview.RoundedImageView

class BannerRecView (val data : ArrayList<FTBanner>, val context: Context) : RecyclerView.Adapter<BannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_banner, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
//        holder?.image?.setOnClickListener {
//            Log.e("imagedata",data.get(position).PhotoName+"")
//        }
        Glide.with(context)
            .load(data[position].BannerImage)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.BannerImage)

    }
}
class BannerViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val BannerImage = view.findViewById<RoundedImageView>(R.id.BannerImage)
}
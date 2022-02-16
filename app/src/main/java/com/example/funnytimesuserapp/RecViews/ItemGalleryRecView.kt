package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.Models.FTItemPhoto
import com.example.funnytimesuserapp.R
import com.makeramen.roundedimageview.RoundedImageView

class ItemGalleryRecView(val data : ArrayList<FTItemPhoto>, val context: Context) : RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_gallery_photo, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        Glide.with(context)
            .load(data[position].PhoneUrl)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.GalleryPhoto)

    }
}
class PhotoViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val GalleryPhoto = view.findViewById<RoundedImageView>(R.id.GalleryPhoto)
}
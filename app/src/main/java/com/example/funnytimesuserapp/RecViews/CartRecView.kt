package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.Interfaces.OnInCartDelete
import com.example.funnytimesuserapp.Interfaces.OnInCartIncrease
import com.example.funnytimesuserapp.Interfaces.OnInCartMinos
import com.example.funnytimesuserapp.Models.FTInCart
import com.example.funnytimesuserapp.R
import com.makeramen.roundedimageview.RoundedImageView
import com.willy.ratingbar.BaseRatingBar
import de.hdodenhof.circleimageview.CircleImageView

class CartRecView(val data : ArrayList<FTInCart>, val context: Context
,val onInCartDelete: OnInCartDelete
,val onInCartMinos: OnInCartMinos
,val onInCartIncrease: OnInCartIncrease) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_cart_item, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {


        Glide.with(context)
            .load(data[position].ItemImage)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.ProductImage)
        holder.ProductTitle.text = data[position].ItemName
        holder.ProductVendorLocation.text = data[position].ItemVendor + "," + data[position].ItemLocation
        holder.ProductRating.rating = data[position].ItemRate!!.toFloat()
        holder.ProductRating.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }
        holder.ProductRatingText.text = data[position].ItemRateText.toString()
        holder.ProductPrice.text = data[position].ItemPrice.toString() + "ر.س"

    }
}

class CartViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val ProductImage = view.findViewById<RoundedImageView>(R.id.ProductImage)
    val ProductTitle = view.findViewById<TextView>(R.id.ProductTitle)
    val ProductVendorLocation = view.findViewById<TextView>(R.id.ProductVendorLocation)
    val ProductPrice = view.findViewById<TextView>(R.id.ProductPrice)
    val ProductRating = view.findViewById<BaseRatingBar>(R.id.ProductRating)
    val ProductRatingText = view.findViewById<TextView>(R.id.ProductRatingText)

    val ProductDelete = view.findViewById<CircleImageView>(R.id.ProductDelete)
    val ProductMinos = view.findViewById<TextView>(R.id.ProductMinos)
    val ProductQuantity = view.findViewById<TextView>(R.id.ProductQuantity)
    val ProductIncrease = view.findViewById<TextView>(R.id.ProductIncrease)

}
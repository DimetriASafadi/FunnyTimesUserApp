package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.Models.FTOrder
import com.example.funnytimesuserapp.R
import com.makeramen.roundedimageview.RoundedImageView

class OrdersRecView (val data : ArrayList<FTOrder>, val context: Context) : RecyclerView.Adapter<OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_ob_orders, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
//        holder?.image?.setOnClickListener {
//            Log.e("imagedata",data.get(position).PhotoName+"")
//        }
        Glide.with(context)
            .load(data[position].OrderItems!![0].ItemDetails.ItemImage)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(holder.OrderImage)

        holder.OrderPrice.text = data[position].OrderTotal
        holder.OrderName.text = "طلب رقم "+data[position].OrderId
        holder.OrderAddress.text = data[position].OrderItems!![0].ItemDetails.ItemAddress
        holder.OrderDate.text = data[position].OrderCreatedAt
        holder.OrderStatus.text = data[position].OrderStatus


    }
}
class OrderViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val OrderImage = view.findViewById<RoundedImageView>(R.id.OrderImage)
    val OrderPrice = view.findViewById<TextView>(R.id.OrderPrice)
    val OrderName = view.findViewById<TextView>(R.id.OrderName)
    val OrderAddress = view.findViewById<TextView>(R.id.OrderAddress)
    val OrderDate = view.findViewById<TextView>(R.id.OrderDate)
    val OrderStatus = view.findViewById<TextView>(R.id.OrderStatus)
}
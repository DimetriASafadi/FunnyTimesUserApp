package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.funnytimesuserapp.Models.FTClinicService
import com.example.funnytimesuserapp.R

class BookServicesRecView (val data : ArrayList<FTClinicService>, val context: Context) : RecyclerView.Adapter<BookServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookServiceViewHolder {
        return BookServiceViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_book_services, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: BookServiceViewHolder, position: Int) {

        holder.ServiceName.text = data[position].ServiceName
        holder.ServicePrice.text = data[position].ServicePrice

    }




}
class BookServiceViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val ServiceName = view.findViewById<TextView>(R.id.ServiceName)
    val ServicePrice = view.findViewById<TextView>(R.id.ServicePrice)
}
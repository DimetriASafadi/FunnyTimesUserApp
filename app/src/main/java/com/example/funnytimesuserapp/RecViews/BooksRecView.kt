package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.Interfaces.OnBookClick
import com.example.funnytimesuserapp.Models.FTBook
import com.example.funnytimesuserapp.Models.FTClinicService
import com.example.funnytimesuserapp.R
import com.makeramen.roundedimageview.RoundedImageView

class BooksRecView(val data : ArrayList<FTBook>, val context: Context,val onBookClick: OnBookClick) : RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_ob_books, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {


        if (data[position].BookDetails != null){
            Glide.with(context)
                .load(data[position].BookDetails!!.BookPropImg)
                .centerCrop()
                .placeholder(R.drawable.ft_broken_image)
                .into(holder.BookImage)
            if (data[position].BookType == 4){
                holder.BookPrice.text = getTotalFromServices(data[position].BookDetails!!.BookPropServices!!).toString()
            }else{
                holder.BookPrice.text = data[position].BookTotal.toString()
            }
            holder.BookName.text = data[position].BookName
            holder.BookAddress.text = data[position].BookDetails!!.BookPropAddress
            holder.BookDate.text = data[position].BookCreatedAt
            holder.BookStatus.text = data[position].BookStatus


            holder.BookStatus.setOnClickListener {

            }
        }
        holder.WholeBook.setOnClickListener {
            onBookClick.OnBookClickListener(data[position])
        }



    }

    fun getTotalFromServices(services:List<FTClinicService>):Double{
        var total = 0.0
        for (i in services.indices) {
                total += services[i].ServicePrice!!.toDouble()
        }
        return total
    }
}
class BookViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val WholeBook = view.findViewById<LinearLayout>(R.id.WholeBook)
    val BookImage = view.findViewById<RoundedImageView>(R.id.BookImage)
    val BookPrice = view.findViewById<TextView>(R.id.BookPrice)
    val BookName = view.findViewById<TextView>(R.id.BookName)
    val BookAddress = view.findViewById<TextView>(R.id.BookAddress)
    val BookDate = view.findViewById<TextView>(R.id.BookDate)
    val BookStatus = view.findViewById<TextView>(R.id.BookStatus)
}
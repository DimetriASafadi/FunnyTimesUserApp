package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.funnytimesuserapp.Models.FTBank
import com.example.funnytimesuserapp.R

class BankRecView(val data : ArrayList<FTBank>, val context: Context) : RecyclerView.Adapter<BankViewHolder>() {


    var selectedone = 999

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        return BankViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item_bank, parent, false))    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {

        if (data[position].BankSelected){
            selectedone = data[position].BankId
            holder.BankSelected.visibility = View.VISIBLE
        }else{
            holder.BankSelected.visibility = View.GONE
        }

        holder.BankName.text = data[position].BankName
        holder.BankIban.text = data[position].BankIban
        holder.BankNumber.text = data[position].BankNumber

        holder.WholeBank.setOnClickListener {
            data[position].BankSelected = !data[position].BankSelected
            notifyDataSetChanged()
        }



    }
}
class BankViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val WholeBank = view.findViewById<LinearLayout>(R.id.WholeBank)
    val BankName = view.findViewById<TextView>(R.id.BankName)
    val BankSelected = view.findViewById<ImageView>(R.id.BankSelected)
    val BankIban = view.findViewById<TextView>(R.id.BankIban)
    val BankNumber = view.findViewById<TextView>(R.id.BankNumber)
}
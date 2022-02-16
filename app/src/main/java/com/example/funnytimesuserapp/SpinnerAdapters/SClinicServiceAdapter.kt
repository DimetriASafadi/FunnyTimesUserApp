package com.example.funnytimesuserapp.SpinnerAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.funnytimesuserapp.Models.FTClinicService
import com.example.funnytimesuserapp.R

class SClinicServiceAdapter (val context: Context, var data: ArrayList<FTClinicService>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.ft_spinner_services, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.ServiceName.text = data[position].ServiceName
        vh.ServicePrice.text = data[position].ServicePrice


        return view
    }

    override fun getItem(position: Int): FTClinicService? {
        return data[position];
    }

    override fun getCount(): Int {
        return data.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(view: View?) {
        val ServiceName = view?.findViewById(R.id.ServiceName) as TextView
        val ServicePrice = view?.findViewById(R.id.ServicePrice) as TextView
    }

}
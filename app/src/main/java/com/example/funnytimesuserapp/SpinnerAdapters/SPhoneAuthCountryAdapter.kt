package com.example.funnytimesuserapp.SpinnerAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.funnytimesuserapp.R

class SPhoneAuthCountryAdapter(val context: Context, var data: ArrayList<String>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.ft_spinner_item_phone_country, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.PSCityName.text = data[position]


        return view
    }

    override fun getItem(position: Int): Any? {
        return data[position];
    }

    override fun getCount(): Int {
        return data.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(view: View?) {
        val PSCityName = view?.findViewById(R.id.PSCityName) as TextView
    }

}
package com.example.funnytimesuserapp.RecViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.Models.FTOrderItem
import com.example.funnytimesuserapp.Models.FTOrderItemAttribute
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.databinding.RecItemOrderItemBinding

class OrderItemsRecView (val data : List<FTOrderItem>, val context: Context) : RecyclerView.Adapter<OrderItemViewHolder>() {

    lateinit var binding:RecItemOrderItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        binding = RecItemOrderItemBinding.inflate(LayoutInflater.from(context))
        return OrderItemViewHolder(binding.root)    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
//        holder?.image?.setOnClickListener {
//            Log.e("imagedata",data.get(position).PhotoName+"")
//        }
        Glide.with(context)
            .load(data[position].ItemDetails.ItemImage)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(binding.ProductImage)

        binding.ProductQuantity.text = "الكمية : "+data[position].ItemQuantity
        binding.ProductTotal.text = "الإجمالي "+data[position].ItemTotal+" ر.س"
        binding.ProductPrice.text = "سعر الوحدة : "+data[position].ItemPrice+" ر.س"
        binding.ProductTitle.text = data[position].ItemDetails.ItemName
        binding.ProductVendorLocation.text = data[position].ItemDetails.ItemAddress
        binding.ProductAttributes.text = GetItemAttributesNames(data[position].ItemAttributes!!)

    }

    fun GetItemAttributesNames(attrbs:List<FTOrderItemAttribute>):String{
        var result = ""
        for (i in 0 until attrbs.size) {
            result = " "+result+attrbs[i].AttributeName+","+result+attrbs[i].AttributeValue+" "
        }
        return result
    }


}
class OrderItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to

}
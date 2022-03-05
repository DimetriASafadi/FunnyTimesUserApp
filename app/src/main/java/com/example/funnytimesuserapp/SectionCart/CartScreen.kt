package com.example.funnytimesuserapp.SectionCart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.Interfaces.OnInCartDelete
import com.example.funnytimesuserapp.Interfaces.OnInCartIncrease
import com.example.funnytimesuserapp.Interfaces.OnInCartMinos
import com.example.funnytimesuserapp.Models.FTInCart
import com.example.funnytimesuserapp.RecViews.CartRecView
import com.example.funnytimesuserapp.SectionCart.SectionPayment.CartPayment
import com.example.funnytimesuserapp.SectionItems.ItemsFuncs
import com.example.funnytimesuserapp.databinding.FtCartScreenBinding

class CartScreen : AppCompatActivity(), OnInCartDelete, OnInCartMinos, OnInCartIncrease {


    val ftIncartItems = ArrayList<FTInCart>()
    lateinit var cartRecView:CartRecView
    val itemFuncs = ItemsFuncs()
    val commonFuncs = CommonFuncs()

    lateinit var binding: FtCartScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtCartScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.backBtn.setOnClickListener {
            finish()
        }
        ftIncartItems.addAll(itemFuncs.GetMyCart(this))
        binding.CartTotal.text = itemFuncs.GetCartTotal(ftIncartItems).toString()
        cartRecView = CartRecView(ftIncartItems,this,this,this,this)
        binding.CartRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)
        binding.CartRecycler.adapter = cartRecView



        binding.PayNow.setOnClickListener {
            if (cartRecView.getSelectedItems().size != 0){
                if (itemFuncs.CheckItems(cartRecView.getSelectedItems())){
                    commonFuncs.showDefaultDialog(this,"ملاحظة !","يجب عليك إختيار منتجات من نفس البائع لمتابعة الشراء")
                    return@setOnClickListener
                }else{
                    val intent = Intent(this,CartPayment::class.java)
                    intent.putExtra("SelectCartItems",cartRecView.getSelectedItems())
                    startActivity(intent)
                }
            }else{
                Toast.makeText(this,"يجب عليك إختيار منتج على الأقل",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

    }

    override fun OnInCartDeleteClick(position: Int) {
        ftIncartItems.removeAt(position)
        itemFuncs.UpdateMyCart(this,ftIncartItems)
        cartRecView.notifyItemRemoved(position)
        cartRecView.notifyItemRangeChanged(position, cartRecView.itemCount - position);
        binding.CartTotal.text = itemFuncs.GetCartTotal(ftIncartItems).toString()
        Log.e("DeleteFromCart",position.toString())
    }

    override fun OnInCartMinosClick(position: Int) {
        if (ftIncartItems[position].ItemQuantity!! > 1 ){
            ftIncartItems[position].ItemQuantity  = ftIncartItems[position].ItemQuantity!! - 1
            itemFuncs.UpdateMyCart(this,ftIncartItems)
            binding.CartTotal.text = itemFuncs.GetCartTotal(ftIncartItems).toString()
            cartRecView.notifyDataSetChanged()
        }
        Log.e("MinosFromCart",position.toString())
    }

    override fun OnInCartIncreaseClick(position: Int) {
        if (ftIncartItems[position].ItemQuantity!! < 99){
            ftIncartItems[position].ItemQuantity  = ftIncartItems[position].ItemQuantity!! + 1
            itemFuncs.UpdateMyCart(this,ftIncartItems)
            binding.CartTotal.text = itemFuncs.GetCartTotal(ftIncartItems).toString()
            cartRecView.notifyDataSetChanged()
        }
        Log.e("IncreaseFromCart",position.toString())
    }


}
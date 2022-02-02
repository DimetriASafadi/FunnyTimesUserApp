package com.example.funnytimesuserapp.Adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.SplashSection.SplashMenu

class SplashAdapter( context: Context) : RecyclerView.Adapter<PagerVH>() {

    private val activity : SplashMenu = context as SplashMenu
    private val myCustomFont : Typeface? = ResourcesCompat.getFont(activity, R.font.montserrat_arabic)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.ft_splashs_item, parent, false)
        )

    //get the size of color array
    override fun getItemCount(): Int = 3

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        val SplashIcon = holder.itemView.findViewById<ImageView>(R.id.SplashIcon)
        val SplashTitle = holder.itemView.findViewById<TextView>(R.id.SplashTitle)
        val SplashDesc = holder.itemView.findViewById<TextView>(R.id.SplashDesc)
//        val SplashCounter = holder.itemView.findViewById<ImageView>(R.id.SplashCounter)


        if(position == 0){
            SplashIcon.setImageResource(R.drawable.ft_splash_a_icon)
//            SplashTitle.text = resources.getText(R.string.splash_a_title)
//            SplashDesc.text = resources.getText(R.string.splash_a_desc)
            SplashTitle.typeface = myCustomFont
            SplashDesc.typeface = myCustomFont
        }
        if(position == 1) {
            SplashIcon.setImageResource(R.drawable.ft_splash_b_icon)
//            SplashTitle.text = resources.getText(R.string.splash_b_title)
//            SplashDesc.text = resources.getText(R.string.splash_b_desc)
            SplashTitle.typeface = myCustomFont
            SplashDesc.typeface = myCustomFont
        }
        if(position == 2) {
            SplashIcon.setImageResource(R.drawable.ft_splash_c_icon)
//            SplashTitle.text = resources.getText(R.string.splash_c_title)
//            SplashDesc.text = resources.getText(R.string.splash_c_desc)
            SplashTitle.typeface = myCustomFont
            SplashDesc.typeface = myCustomFont
        }
    }
}



class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)


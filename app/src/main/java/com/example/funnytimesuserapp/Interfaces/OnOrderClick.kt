package com.example.funnytimesuserapp.Interfaces

import com.example.funnytimesuserapp.Models.FTOrder

interface OnOrderClick {
    fun OnOrderClickListener(ftOrder: FTOrder)
}
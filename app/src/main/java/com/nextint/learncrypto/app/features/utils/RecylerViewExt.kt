package com.nextint.learncrypto.app.features.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setHorizontal() : RecyclerView{
    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    return this
}

fun RecyclerView.setVertical() : RecyclerView{
    layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
    return this
}
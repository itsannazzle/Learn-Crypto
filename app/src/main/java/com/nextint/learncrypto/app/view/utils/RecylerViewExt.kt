package com.nextint.learncrypto.app.view.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setHorizontal(context: Context) : RecyclerView{
    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    return this
}

fun RecyclerView.setVertical(context: Context) : RecyclerView{
    layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
    return this
}
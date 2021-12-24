package com.nextint.learncrypto.app.view.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadImage(url : String){
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun View.makeVisible(){
    this.visibility = View.VISIBLE
}

fun View.makeGone(){
    this.visibility = View.GONE
}
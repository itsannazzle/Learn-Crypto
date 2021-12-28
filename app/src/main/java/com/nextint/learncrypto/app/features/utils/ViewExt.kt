package com.nextint.learncrypto.app.features.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun ImageView.loadImage(url : String){
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun ImageView.cornerImage(url : String, cornerRadius : Int?){
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transform(RoundedCorners(cornerRadius ?: 0))
        .into(this)
}

fun ImageView.circleImage(url: String){
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transform(CircleCrop())
        .into(this)
}

fun View.makeVisible(){
    this.visibility = View.VISIBLE
}

fun View.makeGone(){
    this.visibility = View.GONE
}
package com.nextint.learncrypto.app.features.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.nextint.learncrypto.app.R

fun ImageView.loadImage(url : String){
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.ic_baseline_broken_image_24)
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

fun Dialog.showLoading() {
    with(this)
    {
        setContentView(R.layout.item_loading)
        setCancelable(true)
        setCanceledOnTouchOutside(false)

        val window : Window? = this.window
        window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}


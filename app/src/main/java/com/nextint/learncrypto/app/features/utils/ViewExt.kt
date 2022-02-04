package com.nextint.learncrypto.app.features.utils

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.nextint.learncrypto.app.R
import timber.log.Timber
import java.text.NumberFormat
import java.util.*

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

fun setVisibility(visibile : Boolean) : Int {
    return if (visibile) View.VISIBLE else View.GONE
}

fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment, bundle : Bundle? = null) {
    fragment.arguments = bundle
    fragmentManager.beginTransaction()
        .replace(R.id.mainActivityContainer, fragment)
        .addToBackStack(null)
        .commit()
}

fun convertToUSD(numberToConvert : Long) : String {
    val numberFormat = NumberFormat.getCurrencyInstance();
    numberFormat.maximumFractionDigits = 0
    numberFormat.currency = Currency.getInstance("USD")
    Timber.d(numberFormat.format(numberToConvert))
    return numberFormat.format(numberToConvert)
}

fun convertToPercentage(value : Double) : String {
    return value.toString().plus("%")
}


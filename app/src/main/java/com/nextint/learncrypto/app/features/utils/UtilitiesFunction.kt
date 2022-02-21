package com.nextint.learncrypto.app.features.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nextint.learncrypto.app.R
import timber.log.Timber
import java.text.NumberFormat
import java.util.*

object UtilitiesFunction {
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

    fun convertBooleanToYesOrNo(boolean: Boolean) : Int
    {
        return if (boolean) R.string.yes else R.string.no
    }
}
package com.nextint.learncrypto.app.features.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.InetAddresses
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nextint.learncrypto.app.R
import timber.log.Timber
import java.lang.Exception
import java.net.InetAddress
import java.net.UnknownHostException
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
        return numberFormat.format(numberToConvert)
    }

    fun convertToPercentage(value : Double) : String {
        return value.toString().plus("%")
    }

    fun convertBooleanToYesOrNo(boolean: Boolean) : Int
    {
        return if (boolean) R.string.yes else R.string.no
    }

    fun convertBooleanToActiveOrNotActive(boolean: Boolean) : Int
    {
        return if (boolean) R.string.active else R.string.not_active
    }

    fun convertBooleanToNew(boolean: Boolean) : Int
    {
        return if (boolean) R.string.status_new else R.string.status_not_new
    }

    fun convertBooleanToOpenSource(boolean: Boolean) : Int
    {
        return if (boolean) R.string.open_source else R.string.not_open_source
    }


    fun checkInternetConnection() : Boolean
    {
        return try
        {
            val addressInternet : InetAddress = InetAddress.getByName("www.google.com")
            !addressInternet.equals("")
        } catch (exception : UnknownHostException)
        {
            Timber.e(javaClass.simpleName, "checkInternetConnection | Error = $exception")
            false
        }
    }

    fun openBrowserWithURL(context: Context, stringURL: String)
    {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(stringURL)
        context.startActivity(intent)
    }

    fun openPDFFromUrl(context: Context, stringUrl : String)
    {
        val intent  = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(stringUrl), "application/pdf")
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
    }


}
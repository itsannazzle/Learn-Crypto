package com.nextint.learncrypto.app.features.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.InetAddresses
import android.net.NetworkCapabilities
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

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkNetworkConnection(context: Context) : Boolean
    {
        try
        {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            if (connectivityManager is ConnectivityManager)
            {
                val networkCapabilities : NetworkCapabilities? = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                return if (networkCapabilities != null)
                {
                    when
                    {
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                } else
                {
                    false
                }
            } else
            {
                return false
            }
        } catch (exception : Exception)
        {
            return false
        }
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
}
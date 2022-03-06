package com.nextint.learncrypto.app.bases

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber
import java.lang.Exception
import java.net.InetAddress
import java.net.UnknownHostException

open class BaseService {

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
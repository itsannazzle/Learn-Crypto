package com.nextint.learncrypto.app.features.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.services.FirebaseNotificationService
import com.nextint.learncrypto.app.models.NotificationModel
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_CHANNELID
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_CHANNELNAME
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_ID
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_STATE
import timber.log.Timber
import java.math.RoundingMode
import java.net.InetAddress
import java.net.UnknownHostException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object UtilitiesFunction {
    fun setVisibility(visible : Boolean) : Int {
        return if (visible) View.VISIBLE else View.GONE
    }

    fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment, bundle : Bundle? = null) {
        fragment.arguments = bundle
        fragmentManager.beginTransaction()
            .replace(R.id.mainActivityContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun convertToUSD(numberToConvert : Long) : String {
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0
        numberFormat.roundingMode = RoundingMode.FLOOR
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

    fun setupPushNotification(
        packageContext : FirebaseNotificationService,
        context: Context,
        modelPushNotification: NotificationModel)
    {
        val intent = Intent(packageContext,MainActivity::class.java).apply {
            putExtra(STRING_NOTIFICATION_CHANNELNAME, modelPushNotification.stringChannelName)
            putExtra(STRING_NOTIFICATION_CHANNELID,modelPushNotification.stringChannelId)
            putExtra(STRING_NOTIFICATION_ID,modelPushNotification.stringNotificationId)
            putExtra(STRING_NOTIFICATION_STATE,modelPushNotification.stringNotificationState)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntent : PendingIntent

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S)
        {
            pendingIntent = PendingIntent.getActivity(packageContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        else
        {
            pendingIntent = PendingIntent.getActivity(packageContext, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val mNotificationManager = packageContext.getSystemService(FirebaseMessagingService.NOTIFICATION_SERVICE) as NotificationManager
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(modelPushNotification.stringNotificationTitle)
            .setContentText(modelPushNotification.stringNotificationBody)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.black))
            .setVibrate(longArrayOf(1000))
            .setSound(defaultSoundUri)
            .setStyle(NotificationCompat.BigTextStyle())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(
                modelPushNotification.stringChannelId ?: STRING_NOTIFICATION_CHANNELID,
                modelPushNotification.stringChannelName ?: STRING_NOTIFICATION_CHANNELNAME,
                NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000)
            notificationBuilder.setChannelId(modelPushNotification.stringChannelId ?: STRING_NOTIFICATION_CHANNELID)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = notificationBuilder.build()

        mNotificationManager.notify(modelPushNotification.stringNotificationId ?: 1, notification)

    }


}
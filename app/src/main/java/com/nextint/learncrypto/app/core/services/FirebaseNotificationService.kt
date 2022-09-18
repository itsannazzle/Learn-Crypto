package com.nextint.learncrypto.app.core.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.models.NotificationModel
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_CHANNELID
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_CHANNELNAME
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_ID
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_STATE

class FirebaseNotificationService : FirebaseMessagingService()
{
    companion object
    {
        var token : String? = null
    }

    override fun onNewToken(tokenFromService: String)
    {
        token = tokenFromService
        super.onNewToken(tokenFromService)
    }

    override fun onMessageReceived(payloadMessage : RemoteMessage)
    {
        super.onMessageReceived(payloadMessage)
        val notificationModel = NotificationModel()
        notificationModel.stringChannelName = payloadMessage.data[STRING_NOTIFICATION_CHANNELNAME]
        notificationModel.stringChannelId = payloadMessage.data[STRING_NOTIFICATION_CHANNELID]
        notificationModel.stringNotificationId = payloadMessage.data[STRING_NOTIFICATION_ID]?.toInt() ?: 1
        notificationModel.stringNotificationState = payloadMessage.data[STRING_NOTIFICATION_STATE]
        notificationModel.stringNotificationTitle = payloadMessage.notification?.title
        notificationModel.stringNotificationBody = payloadMessage.notification?.body

        UtilitiesFunction.setupPushNotification(this,this, notificationModel)
    }
}
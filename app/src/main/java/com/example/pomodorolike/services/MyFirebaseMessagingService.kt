package com.example.pomodorolike.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.pomodorolike.R
import com.example.pomodorolike.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "com.example.pomodorolike"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            channelId
        )
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setColor(resources.getColor(R.color.orange))
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setShowWhen(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(message)

//        builder = builder.setContent(getRemoteView(title,message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,builder.build())
    }

 /*   private fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteViews = RemoteViews("com.example.pomodorolike", R.layout.push_notification)

        remoteViews.setTextViewText(R.id.title_of_notification, title)
        remoteViews.setTextViewText(R.id.message_of_notification, message)
        remoteViews.setImageViewResource(R.id.notification_logo, R.drawable.ic_notification_icon)
        return remoteViews
    }*/
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.notification != null){
            remoteMessage.notification!!.body?.let {
                remoteMessage.notification!!.title?.let { it1 ->
                    generateNotification(
                        it1,
                        it
                    )
                }
            }
        }
    }
}
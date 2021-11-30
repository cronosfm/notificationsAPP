package com.example.notificationapp

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MainActivity : Activity() {

    lateinit var btnNotification: Button
    private val channelID = "channelID"
    private val channelName = "channelName"
    private val notifiactionID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnNotification = findViewById(R.id.btnSendNotify)


        btnNotification.setOnClickListener {
            println("hola")
            sendNotification()
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelID, channelName, importance).apply {}

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(): Unit {
        println("soy una notificación")

        val buttonIntent = Intent(this, Intent.ACTION_VIEW::class.java)
        buttonIntent.putExtra("argumentoExtra", "Notificación Recibida")
        val buttonPendingIntent = PendingIntent.getActivity(
            this, 0, buttonIntent, 0
        )

        val action = NotificationCompat.Action.Builder(
            R.drawable.common_full_open_on_phone,
            "Marcar como vista",
            buttonPendingIntent
        ).build()

//        val extender = NotificationCompat.Extend

        val notification = NotificationCompat.Builder(this, channelID).also {
            it.setContentTitle("soy una notificación")
            it.setContentText("me enviaron desde un Weareable")
            it.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
            it.priority = NotificationCompat.PRIORITY_HIGH
            it.addAction(action)
            it.setAutoCancel(true)
        }.build()

        NotificationManagerCompat.from(this).apply {
            notify(notifiactionID,notification)
        }
    }
}
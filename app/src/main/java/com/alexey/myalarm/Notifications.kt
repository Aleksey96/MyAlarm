package com.alexey.myalarm
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.icu.util.ULocale
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import java.util.*

class Notifications {

    private lateinit var nm: NotificationManager
    private val pattern = longArrayOf(1000, 1500, 1000, 1500,1000, 1500, 1000, 1500,1000, 1500, 1000, 1500)//sleep,vibrate
    private val MY_NOTIFICATION_ID: Int = 1
    private val tickerText: CharSequence = "Notification Message"
    private val contentTitle: CharSequence = "Notification"
    private val contentText: CharSequence = "Леха,Ты должен встать!"


    private fun setNotificationAtributs(notification: Notification,context: Context?) {
        notification.vibrate = pattern
    }

    fun showNotification(context: Context?) {
        val soundAlarm=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) as Uri
        nm = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context,AlarmFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context,"channel")
        val bitmapFactory=BitmapFactory.decodeResource(context.resources,R.drawable.hj)

        builder
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setSound(soundAlarm)
            .setLights(Color.BLUE,500,500)
            /*.setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.ic_launcher_background
                )
            )*/
            .setTicker(tickerText)
            .setWhen(System.currentTimeMillis())
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setAutoCancel(true)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setShowWhen(true)
            .setColor(Color.GREEN)
            .setProgress(100,20,false)
            .setTimeoutAfter(600000)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmapFactory))
            .setCategory(Notification.CATEGORY_ALARM)
            //.setFullScreenIntent(pendingIntent,true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("ИнтерфаксИнтерфакс В России зарегистрировано шесть новых случаев коронавирус 80 фотоЕщё 2 видео В России за сутки зарегистрировано шесть новых заболевших коронавирусом, сообщает оперативный штаб. Один случай зарегистрирован в Нижнем Новгороде, 5 случаев в Москве. Все граждане РФ посещали Италию в последние две недели."))

        //генерируем объект уведомления
        val notification = builder.build()
        //setNotificationAtributs(notification,context)
        nm.notify(MY_NOTIFICATION_ID, notification)
    }
}
package com.alexey.myalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class Receiver:BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) {
            Log.e("flag","Null")
            return
        }
        Notifications().showNotification(context)
    }
}
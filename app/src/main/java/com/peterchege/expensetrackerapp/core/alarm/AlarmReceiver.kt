package com.peterchege.expensetrackerapp.core.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message") ?: return
        println("Alarm Triggered $message")
    }
}
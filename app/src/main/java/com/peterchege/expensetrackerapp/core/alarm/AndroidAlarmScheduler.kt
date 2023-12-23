package com.peterchege.expensetrackerapp.core.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import java.time.ZoneId

class AndroidAlarmScheduler(
    private val appContext: Context,
) : AlarmScheduler {

    private val alarmManager = appContext.getSystemService<AlarmManager>()


    @SuppressLint("ScheduleExactAlarm")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun schedule(item: AlarmItem) {
        val intent = Intent(appContext, AlarmReceiver::class.java).apply {
            putExtra("message", item.message)
        }
        if (alarmManager == null) return
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                appContext,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(item: AlarmItem) {
        alarmManager?.cancel(
            PendingIntent.getBroadcast(
                appContext,
                item.hashCode(),
                Intent(appContext,AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

}
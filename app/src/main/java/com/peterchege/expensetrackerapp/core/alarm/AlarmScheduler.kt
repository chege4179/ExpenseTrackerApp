package com.peterchege.expensetrackerapp.core.alarm

interface AlarmScheduler {

    fun schedule(item: AlarmItem)

    fun cancel(item: AlarmItem)
}
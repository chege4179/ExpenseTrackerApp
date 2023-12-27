package com.peterchege.expensetrackerapp.core.alarm

import java.time.LocalDateTime

data class AlarmItem(
    val time:LocalDateTime,
    val message:String
)
package com.peterchege.expensetrackerapp.core.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.*


fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}

fun toNormalDate(date:LocalDate):Date{
    val instant = date.atStartOfDay().toInstant(ZoneOffset.UTC)
    return Date.from(instant)
}


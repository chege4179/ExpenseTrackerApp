package com.peterchege.expensetrackerapp.core.util

import java.time.*
import java.util.*


fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}

fun localDateTimeToDate(localDate: LocalDate, localTime: LocalTime): Date {
    val localDateTime = LocalDateTime.of(localDate, localTime)
    val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
    return Date.from(instant)
}
fun randomColorCode(): String {
    val random = Random()
    val nextInt = random.nextInt(0xffffff + 1)
    return String.format("#%06x", nextInt).drop(1).capitalize(Locale.ROOT)
}

fun generateAvatarURL(name:String):String{
    val splitname = name.split(" ").joinToString("+")
    val color = randomColorCode()
    return "https://ui-avatars.com/api/?background=${color}&color=fff&name=${splitname}&bold=true&fontsize=0.6&rounded=true"

}
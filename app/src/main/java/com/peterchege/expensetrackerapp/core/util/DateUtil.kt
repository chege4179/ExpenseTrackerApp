/*
 * Copyright 2023 Expense Tracker App By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.expensetrackerapp.core.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


fun previousDay(date: String): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val parsedDate = formatter.parse(date)
    val calendar = Calendar.getInstance()
    calendar.time = parsedDate
    calendar.add(Calendar.DATE, -1)
    return formatter.format(calendar.time)
}

fun generate7daysPriorDate(date: String): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val parsedDate = formatter.parse(date)
    val calendar = Calendar.getInstance()
    calendar.time = parsedDate
    calendar.add(Calendar.DATE, -6)
    return formatter.format(calendar.time)
}

fun datesBetween(startDate: String, endDate: String): List<String> {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val start = formatter.parse(startDate)
    val end = formatter.parse(endDate)
    val dates = mutableListOf<String>()
    var current = start

    while (current <= end) {
        dates.add(formatter.format(current))
        val calendar = Calendar.getInstance()
        calendar.time = current
        calendar.add(Calendar.DATE, 1)
        current = calendar.time
    }

    return dates
}

fun firstDayOfMonth(date: String): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val parsedDate = formatter.parse(date)
    val calendar = Calendar.getInstance()
    calendar.time = parsedDate
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    return formatter.format(calendar.time)
}

fun generateFormatDate(date: LocalDate):String{
    var dateCount:String;
    var monthCount:String;
    if (date.dayOfMonth < 10){
        dateCount = "0${date.dayOfMonth}"
    }else{
        dateCount = date.dayOfMonth.toString()
    }
    if (date.monthValue < 10){
        monthCount ="0${date.monthValue}"
    }else{
        monthCount = date.monthValue.toString()
    }
    return "${dateCount}/${monthCount}/${date.year}"

}



fun getWeekDates(dateString: String): List<String> {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = dateFormat.parse(dateString)
    val calendar = Calendar.getInstance()
    calendar.time = date
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    calendar.add(Calendar.DATE, -dayOfWeek + 1)
    val weekDates = mutableListOf<String>()
    for (i in 1..7) {
        weekDates.add(dateFormat.format(calendar.time))
        calendar.add(Calendar.DATE, 1)
    }
    return weekDates
}


fun getMonthDates(dateString: String): List<String> {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = dateFormat.parse(dateString)
    val calendar = Calendar.getInstance()
    calendar.time = date
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val monthDates = mutableListOf<String>()
    for (i in 1..daysInMonth) {
        monthDates.add(dateFormat.format(calendar.time))
        calendar.add(Calendar.DATE, 1)
    }
    return monthDates
}

fun getActualDayOfWeek(dateString: String): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    val date = format.parse(dateString)
    val calendar = Calendar.getInstance()
    calendar.time = date
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    return when (dayOfWeek) {
        Calendar.MONDAY -> "Monday"
        Calendar.TUESDAY -> "Tuesday"
        Calendar.WEDNESDAY -> "Wednesday"
        Calendar.THURSDAY -> "Thursday"
        Calendar.FRIDAY -> "Friday"
        Calendar.SATURDAY -> "Saturday"
        Calendar.SUNDAY -> "Sunday"
        else -> ""
    }
}
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

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

private fun String.toLocalDate(): LocalDate = LocalDate.parse(this, dateFormatter)

fun previousDay(date: String): String {
    return dateFormatter.format(date.toLocalDate().minusDays(1))
}

fun generate7daysPriorDate(date: String): String {
    return dateFormatter.format(date.toLocalDate().minusDays(6))
}

fun datesBetween(startDate: String, endDate: String): List<String> {
    val end = endDate.toLocalDate()
    return generateSequence(startDate.toLocalDate()) { it.plusDays(1) }
        .takeWhile { !it.isAfter(end) }
        .map { dateFormatter.format(it) }
        .toList()
}

fun firstDayOfMonth(date: String): String {
    return dateFormatter.format(date.toLocalDate().withDayOfMonth(1))
}

fun generateFormatDate(date: LocalDate): String {
    return dateFormatter.format(date)
}

// Weeks start on Sunday
fun getWeekDates(dateString: String): List<String> {
    val date = dateString.toLocalDate()
    val sunday = date.minusDays((date.dayOfWeek.value % 7).toLong())
    return (0L until 7L).map { dateFormatter.format(sunday.plusDays(it)) }
}

fun getMonthDates(dateString: String): List<String> {
    val firstDay = dateString.toLocalDate().withDayOfMonth(1)
    return (0L until firstDay.lengthOfMonth()).map { dateFormatter.format(firstDay.plusDays(it)) }
}

fun getActualDayOfWeek(dateString: String): String {
    return dateString.toLocalDate().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
}

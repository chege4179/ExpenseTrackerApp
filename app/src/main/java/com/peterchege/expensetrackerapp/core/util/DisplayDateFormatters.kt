/*
 * Copyright 2026 Expense Tracker App By Peter Chege
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
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val displayDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
private val displayTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

/** Formats a date for display, e.g. "10 Oct 2026" */
fun LocalDate.toDisplayDate(): String = displayDateFormatter.format(this)

/** Formats a time for display, e.g. "14:05" */
fun LocalTime.toDisplayTime(): String = displayTimeFormatter.format(this)

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

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DateUtilTest {

    @Test
    fun previousDay_crossesMonthAndYearBoundaries() {
        assertEquals("31/12/2025", previousDay("01/01/2026"))
        assertEquals("29/02/2024", previousDay("01/03/2024"))
    }

    @Test
    fun generate7daysPriorDate_returnsSixDaysBefore() {
        assertEquals("04/10/2026", generate7daysPriorDate("10/10/2026"))
    }

    @Test
    fun datesBetween_isInclusive() {
        assertEquals(
            listOf("30/09/2026", "01/10/2026", "02/10/2026"),
            datesBetween("30/09/2026", "02/10/2026")
        )
        assertEquals(emptyList<String>(), datesBetween("02/10/2026", "01/10/2026"))
    }

    @Test
    fun firstDayOfMonth_returnsFirstDay() {
        assertEquals("01/10/2026", firstDayOfMonth("10/10/2026"))
    }

    @Test
    fun generateFormatDate_zeroPadsDayAndMonth() {
        assertEquals("05/03/2026", generateFormatDate(LocalDate.of(2026, 3, 5)))
        assertEquals("25/12/2026", generateFormatDate(LocalDate.of(2026, 12, 25)))
    }

    @Test
    fun getWeekDates_startsOnSunday() {
        // 10/10/2026 is a Saturday
        val week = getWeekDates("10/10/2026")
        assertEquals(7, week.size)
        assertEquals("04/10/2026", week.first())
        assertEquals("10/10/2026", week.last())
        // A Sunday is the first day of its own week
        assertEquals("11/10/2026", getWeekDates("11/10/2026").first())
    }

    @Test
    fun getMonthDates_coversWholeMonth() {
        val february = getMonthDates("15/02/2024")
        assertEquals(29, february.size)
        assertEquals("01/02/2024", february.first())
        assertEquals("29/02/2024", february.last())
    }

    @Test
    fun getActualDayOfWeek_returnsEnglishName() {
        assertEquals("Saturday", getActualDayOfWeek("10/10/2026"))
        assertEquals("Sunday", getActualDayOfWeek("11/10/2026"))
    }
}

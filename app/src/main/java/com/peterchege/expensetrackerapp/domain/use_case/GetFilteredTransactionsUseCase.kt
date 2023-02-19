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
package com.peterchege.expensetrackerapp.domain.use_case

import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.core.util.*
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.util.Locale.filter
import javax.inject.Inject


class GetFilteredTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    val todayDate = generateFormatDate(date = LocalDate.now())
    val yesterdayDate = previousDay(date = todayDate)
    val weekDates = getWeekDates(dateString = todayDate)
    val monthDates = getMonthDates(dateString = todayDate)
    val oneWeekEarlierDate = generate7daysPriorDate(date = todayDate)
    val daysInThe7daysPrior = datesBetween(startDate = oneWeekEarlierDate, endDate = todayDate)

    operator fun invoke(filter: String): Flow<List<TransactionEntity>> {
        when (filter) {
            FilterConstants.TODAY -> {
                return repository.getTransactionsForACertainDay(date = todayDate)
            }
            FilterConstants.YESTERDAY -> {
                return repository.getTransactionsForACertainDay(date = yesterdayDate)
            }
            FilterConstants.THIS_WEEK -> {
                return repository.getTransactionsBetweenTwoDates(dates = weekDates)
            }
            FilterConstants.LAST_7_DAYS -> {
                return repository.getTransactionsBetweenTwoDates(dates = daysInThe7daysPrior)
            }
            FilterConstants.THIS_MONTH -> {
                return repository.getTransactionsBetweenTwoDates(dates = monthDates)
            }
            FilterConstants.ALL -> {
                return repository.getAllTransactions()
            }
            else ->  {
                return flow { emptyList<TransactionEntity>() }
            }
        }

    }
}
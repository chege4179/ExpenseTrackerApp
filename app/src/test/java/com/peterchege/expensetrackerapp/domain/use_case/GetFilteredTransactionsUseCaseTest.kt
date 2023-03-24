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
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetFilteredTransactionsUseCaseTest {
    private val repository: TransactionRepository = mockk()

    private val getFilteredTransactionUseCase = GetFilteredTransactionsUseCase(repository)

    val mockedTransactions: Flow<List<TransactionEntity>> = mockk()

    @Test
    fun `When passed today date is passed ,assert that getTransactionsForACertainDay runs `() =
        runTest {
            coEvery { repository.getTransactionsForACertainDay(any()) } returns mockedTransactions
            coEvery { repository.getTransactionsBetweenTwoDates(any()) } returns mockedTransactions

            val result = getFilteredTransactionUseCase(FilterConstants.TODAY)
            coVerify(atLeast = 1, atMost = 1) {
                repository.getTransactionsForACertainDay(any())
            }


        }

    @Test
    fun `When passed this week, this month, last 7 days getTransactionsBetweenTwoDates runs`() =
        runTest {
            coEvery { repository.getTransactionsForACertainDay(any()) } returns mockedTransactions
            coEvery { repository.getTransactionsBetweenTwoDates(any()) } returns mockedTransactions
            val result = getFilteredTransactionUseCase(FilterConstants.THIS_WEEK)

            coVerify(atLeast = 1) {
                repository.getTransactionsBetweenTwoDates(any())
            }
        }
}
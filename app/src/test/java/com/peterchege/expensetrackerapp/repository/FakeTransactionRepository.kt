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
package com.peterchege.expensetrackerapp.repository

import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTransactionRepository :TransactionRepository {
    private val dummyTransactions = listOf(1,2,3,4,5,6).map {
        TransactionEntity(
            transactionId = it.toString(),
            transactionCreatedAt = it.toString(),
            transactionUpdatedAt = it.toString(),
            transactionCategoryId = it.toString(),
            transactionCreatedOn = it.toString(),
            transactionUpdatedOn = it.toString(),
            transactionAmount = it,
            transactionName = it.toString(),
        )
    }

    override suspend fun createTransaction(transaction: Transaction) {
    }

    override fun getAllTransactions(): Flow<List<TransactionEntity>> {
        return flow { dummyTransactions }
    }

    override suspend fun getTransactionById(transactionId: String): TransactionEntity? {
        return dummyTransactions.find { it.transactionId == transactionId }

    }

    override fun getTransactionsForACertainDay(date: String): Flow<List<TransactionEntity>> {
        return flow { dummyTransactions }
    }

    override fun getTransactionsBetweenTwoDates(dates: List<String>): Flow<List<TransactionEntity>> {
        return flow { dummyTransactions }
    }

    override fun searchTransactions(
        dates: List<String>,
        categoryId: String
    ): Flow<List<TransactionEntity>> {
        return flow { dummyTransactions }
    }

    override suspend fun deleteTransactionById(transactionId: String) {
        TODO("Not yet implemented")
    }
}
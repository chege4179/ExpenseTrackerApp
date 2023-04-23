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
package com.peterchege.expensetrackerapp.data

import com.peterchege.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import com.peterchege.expensetrackerapp.domain.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val db: ExpenseTrackerAppDatabase,
) : TransactionRepository {
    override suspend fun createTransaction(transaction: Transaction) {
        return db.transactionEntityDao.insertTransaction(
            transactionEntity = transaction.toEntity()
        )
    }

    override suspend fun getTransactionById(transactionId:String): TransactionEntity? {
        return db.transactionEntityDao.getTransactionById(id = transactionId)
    }

    override fun getAllTransactions(): Flow<List<TransactionEntity>> {
        return db.transactionEntityDao.getTransactions()

    }

    override fun getTransactionsForACertainDay(date: String): Flow<List<TransactionEntity>> {
        return db.transactionEntityDao.getTransactionsForACertainDay(date = date)
    }

    override fun getTransactionsBetweenTwoDates(dates: List<String>): Flow<List<TransactionEntity>> {
        return db.transactionEntityDao.getTransactionsBetweenTwoDates(dates = dates)
    }

    override fun searchTransactions(
        dates: List<String>,
        categoryId: String
    ): Flow<List<TransactionEntity>> {
        return db.transactionEntityDao.searchTransactions(dates = dates, categoryId = categoryId)
    }

    override suspend fun deleteTransactionById(transactionId: String) {
        return db.transactionEntityDao.deleteTransactionById(id = transactionId)
    }

    override fun getTransactionByCategory(categoryId: String): Flow<List<TransactionEntity>> {
        return db.transactionEntityDao.getTransactionsByCategory(categoryId = categoryId)
    }

}
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
import com.peterchege.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import com.peterchege.expensetrackerapp.domain.repository.TransactionCategoryRepository
import com.peterchege.expensetrackerapp.domain.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionCategoryRepositoryImpl @Inject constructor(
    private val db:ExpenseTrackerAppDatabase

) :TransactionCategoryRepository{
    override suspend fun saveTransactionCategory(transactionCategory: TransactionCategory) {
        return db.transactionCategoryEntityDao.insertTransactionCategory(
            transactionCategoryEntity = transactionCategory.toEntity())
    }

    override suspend fun getTransactionCategoryById(transactionId: String): TransactionCategoryEntity? {
        return db.transactionCategoryEntityDao.getTransactionCategoryById(id = transactionId)
    }

    override fun getAllTransactionCategories(): Flow<List<TransactionCategoryEntity>> {
        return db.transactionCategoryEntityDao.getTransactionCategories()
    }

    override suspend fun getTransactionCategoryByName(name: String): TransactionCategoryEntity? {
        return db.transactionCategoryEntityDao.getTransactionCategoryByName(name = name)
    }

    override suspend fun updateTransactionCategory(
        transactionCategoryId: String,
        transactionCategoryName: String
    ) {
        return db.transactionCategoryEntityDao.updateExpenseCategoryName(
            id = transactionCategoryId,
            name = transactionCategoryName,
        )

    }

    override suspend fun deleteTransactionCategory(transactionCategoryId: String) {
        return db.transactionCategoryEntityDao.deleteTransactionCategoryById(
            id = transactionCategoryId)
    }
}
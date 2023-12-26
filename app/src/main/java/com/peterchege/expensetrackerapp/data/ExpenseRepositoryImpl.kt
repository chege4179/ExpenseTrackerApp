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

import com.peterchege.expensetrackerapp.core.analytics.analytics.AnalyticsHelper
import com.peterchege.expensetrackerapp.core.analytics.analytics.logNewExpense
import com.peterchege.expensetrackerapp.core.di.IoDispatcher
import com.peterchege.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseEntity
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.repository.ExpenseRepository
import com.peterchege.expensetrackerapp.domain.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val db:ExpenseTrackerAppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val analyticsHelper: AnalyticsHelper,
): ExpenseRepository {
    override suspend fun createExpense(expense: Expense) {
        analyticsHelper.logNewExpense()
        withContext(ioDispatcher){
            db.expenseEntityDao.insertExpense(expenseEntity = expense.toEntity())
        }
    }

    override suspend fun getExpenseById(expenseId: String): ExpenseEntity? {
        return withContext(ioDispatcher){
            db.expenseEntityDao.getExpenseById(expenseId)
        }
    }

    override fun getAllExpenses(): Flow<List<ExpenseEntity>> {
        return db.expenseEntityDao.getAllExpenses().flowOn(ioDispatcher)
    }

    override suspend fun deleteExpenseById(expenseId: String) {
        withContext(ioDispatcher){
            db.expenseEntityDao.deleteExpenseById(id = expenseId)
        }
    }
    override fun getExpensesByCategory(categoryId: String): Flow<List<ExpenseEntity>> {
        return db.expenseEntityDao.getExpensesByCategory(id = categoryId).flowOn(ioDispatcher)
    }

    override suspend fun updateExpense(
        expenseName: String,
        expenseAmount: Int,
        expenseUpdatedAt: String,
        expenseUpdatedOn: String
    ) {
        TODO("Not yet implemented")
    }


}
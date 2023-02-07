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
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseEntity
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.repository.ExpenseRepository
import com.peterchege.expensetrackerapp.domain.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val db:ExpenseTrackerAppDatabase
): ExpenseRepository {
    override suspend fun createExpense(expense: Expense) {
        return db.expenseEntityDao.insertExpense(expenseEntity = expense.toEntity())
    }

    override fun getAllExpenses(): Flow<List<ExpenseEntity>> {
        return db.expenseEntityDao.getAllExpenses()
    }

    override suspend fun deleteExpenseById(expenseId: String) {
        return db.expenseEntityDao.deleteExpenseById(id = expenseId)
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
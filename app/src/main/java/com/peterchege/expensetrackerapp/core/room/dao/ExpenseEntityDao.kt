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
package com.peterchege.expensetrackerapp.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseEntityDao {

    @Query("SELECT * FROM Expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM Expenses WHERE expenseId =:expenseId")
    suspend fun getExpenseById(expenseId:String):ExpenseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Query("DELETE FROM Expenses WHERE expenseId = :id")
    suspend fun deleteExpenseById(id: String)

    @Query("SELECT * FROM Expenses WHERE expenseCategoryId = :id")
    fun getExpensesByCategory(id: String): Flow<List<ExpenseEntity>>

    @Query("DELETE FROM Expenses")
    suspend fun deleteAllExpenseCategories()

}
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
import kotlinx.coroutines.flow.Flow


@Dao
interface ExpenseCategoryEntityDao {

    @Query("SELECT * FROM ExpenseCategory")
    fun getExpenseCategories(): Flow<List<ExpenseCategoryEntity>>

    @Query("SELECT * FROM ExpenseCategory WHERE expenseCategoryName =:name")
    suspend fun getExpenseCategoryByName(name:String):ExpenseCategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenseCategory(expenseCategoryEntity: ExpenseCategoryEntity)


    @Query("DELETE FROM ExpenseCategory WHERE expenseCategoryId = :id")
    suspend fun deleteExpenseCategoryById(id: String)

    @Query("DELETE FROM ExpenseCategory")
    suspend fun deleteAllExpenseCategories()

    @Query("UPDATE ExpenseCategory SET expenseCategoryName = :name WHERE expenseCategoryId = :id")
    suspend fun updateExpenseCategoryName(id:String,name:String)

}

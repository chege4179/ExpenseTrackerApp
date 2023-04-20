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
package com.peterchege.expensetrackerapp.core.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.peterchege.expensetrackerapp.core.room.dao.ExpenseCategoryEntityDao
import com.peterchege.expensetrackerapp.core.room.dao.ExpenseEntityDao
import com.peterchege.expensetrackerapp.core.room.dao.IncomeEntityDao
import com.peterchege.expensetrackerapp.core.room.dao.TransactionCategoryEntityDao
import com.peterchege.expensetrackerapp.core.room.dao.TransactionEntityDao
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseEntity
import com.peterchege.expensetrackerapp.core.room.entities.IncomeEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.core.room.type_converters.DateConverter

@TypeConverters(DateConverter::class)
@Database(
    entities = [
        ExpenseCategoryEntity::class,
        TransactionCategoryEntity::class,
        ExpenseEntity::class,
        TransactionEntity::class,
        IncomeEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class ExpenseTrackerAppDatabase : RoomDatabase() {


    abstract val expenseCategoryEntityDao: ExpenseCategoryEntityDao

    abstract val transactionCategoryEntityDao: TransactionCategoryEntityDao

    abstract val expenseEntityDao:ExpenseEntityDao

    abstract val transactionEntityDao:TransactionEntityDao

    abstract val incomeEntityDao:IncomeEntityDao

}
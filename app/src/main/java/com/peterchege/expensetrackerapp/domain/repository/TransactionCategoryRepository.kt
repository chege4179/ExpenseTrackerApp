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
package com.peterchege.expensetrackerapp.domain.repository

import com.peterchege.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import kotlinx.coroutines.flow.Flow

interface TransactionCategoryRepository {

    suspend fun saveTransactionCategory(transactionCategory: TransactionCategory)

    fun getAllTransactionCategories(): Flow<List<TransactionCategoryEntity>>

    suspend fun getTransactionCategoryById(transactionId:String):TransactionCategoryEntity?

    suspend fun getTransactionCategoryByName(name:String): TransactionCategoryEntity?

    suspend fun updateTransactionCategory(transactionCategoryId:String,transactionCategoryName:String)

    suspend fun deleteTransactionCategory(transactionCategoryId:String)


}
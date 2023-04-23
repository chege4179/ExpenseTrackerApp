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
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionEntityDao {

    @Query("SELECT * FROM Transactions")
    fun getTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity)

    @Query("DELETE FROM Transactions WHERE transactionId = :id")
    suspend fun deleteTransactionById(id: String)

    @Query("SELECT * FROM Transactions WHERE transactionId = :id")
    suspend fun getTransactionById(id: String):TransactionEntity?

    @Query("DELETE FROM Transactions")
    suspend fun deleteAllTransactions()

    @Query("SELECT * FROM Transactions WHERE transactionCreatedOn = :date")
    fun getTransactionsForACertainDay(date:String):Flow<List<TransactionEntity>>

    @Query("SELECT * FROM Transactions WHERE transactionCreatedOn IN (:dates)")
    fun getTransactionsBetweenTwoDates(dates:List<String>):Flow<List<TransactionEntity>>

    @Query("SELECT * FROM Transactions WHERE transactionCreatedOn IN (:dates) AND transactionCategoryId = :categoryId")
    fun searchTransactions(dates:List<String>,categoryId:String):Flow<List<TransactionEntity>>

    @Query("SELECT * FROM Transactions WHERE transactionCategoryId = :categoryId")
    fun getTransactionsByCategory(categoryId:String):Flow<List<TransactionEntity>>

}
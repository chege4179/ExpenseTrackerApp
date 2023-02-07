package com.peterchege.expensetrackerapp.domain.repository

import com.peterchege.expensetrackerapp.core.room.entities.ExpenseEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun createTransaction(transaction: Transaction)

    fun getAllTransactions(): Flow<List<TransactionEntity>>

    fun getTransactionById(transactionId:String):TransactionEntity?

    fun getTransactionsForACertainDay(date:String):Flow<List<TransactionEntity>>

    fun getTransactionsBetweenTwoDates(dates:List<String>):Flow<List<TransactionEntity>>
}
package com.peterchege.expensetrackerapp.domain.repository

import com.peterchege.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import kotlinx.coroutines.flow.Flow

interface TransactionCategoryRepository {

    suspend fun saveTransactionCategory(transactionCategory: TransactionCategory)

    fun getAllTransactionCategories(): Flow<List<TransactionCategoryEntity>>

    suspend fun getTransactionCategoryByName(name:String): TransactionCategoryEntity?

    suspend fun updateTransactionCategory(transactionCategoryId:String,transactionCategoryName:String)

    suspend fun deleteTransactionCategory(transactionCategoryId:String)
}
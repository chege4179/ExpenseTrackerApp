package com.peterchege.expensetrackerapp.domain.repository

import com.peterchege.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import kotlinx.coroutines.flow.Flow

interface ExpenseCategoryRepository {
    suspend fun saveExpenseCategory(expenseCategory: ExpenseCategory)

    fun getAllExpenseCategories(): Flow<List<ExpenseCategoryEntity>>

    suspend fun getExpenseCategoryByName(name:String):ExpenseCategoryEntity?

    suspend fun updateExpenseCategory(expenseCategoryId:String,expenseCategoryName:String)

    suspend fun deleteExpenseCategory(expenseCategoryId:String)

}
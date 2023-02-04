package com.peterchege.expensetrackerapp.domain.repository

import com.peterchege.expensetrackerapp.core.room.entities.ExpenseEntity
import com.peterchege.expensetrackerapp.domain.models.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    suspend fun createExpense(expense: Expense)

    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    suspend fun deleteExpenseById(expenseId:String)

    suspend fun updateExpense(
        expenseName:String,
        expenseAmount:Int,
        expenseUpdatedAt:String,
        expenseUpdatedOn:String,
    )

}
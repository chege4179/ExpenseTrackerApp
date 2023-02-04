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
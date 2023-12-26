package com.peterchege.expensetrackerapp.domain.use_case

import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.models.TransactionInfo
import com.peterchege.expensetrackerapp.domain.repository.ExpenseCategoryRepository
import com.peterchege.expensetrackerapp.domain.repository.ExpenseRepository
import com.peterchege.expensetrackerapp.domain.toExternalModel
import javax.inject.Inject


data class ExpenseInfo(
    val expense: Expense?,
    val expenseCategory: ExpenseCategory,
)

class GetSingleExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val  expenseCategoryRepository: ExpenseCategoryRepository,
) {


    suspend operator fun invoke(expenseId:String): ExpenseInfo?{
        val expense = expenseRepository.getExpenseById(expenseId = expenseId)
        val expenseCategory = expense?.let {
            expenseCategoryRepository.getExpenseCategoryById(
                expenseCategoryId = it.expenseCategoryId
            )
        }
        if (expenseCategory != null) {
            return ExpenseInfo(
                expense = expense.toExternalModel(),
                expenseCategory = expenseCategory.toExternalModel(),
            )
        }else{
            return null
        }

    }
}
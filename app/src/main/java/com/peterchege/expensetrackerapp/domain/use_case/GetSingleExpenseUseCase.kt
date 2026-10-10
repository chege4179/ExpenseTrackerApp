/*
 * Copyright 2026 Expense Tracker App By Peter Chege
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
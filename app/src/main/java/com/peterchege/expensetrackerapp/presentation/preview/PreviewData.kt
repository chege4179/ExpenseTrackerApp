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
package com.peterchege.expensetrackerapp.presentation.preview

import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.models.Income
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory

/**
 * Sample data used by Compose previews
 */
object PreviewData {

    val transactionCategories = listOf(
        TransactionCategory(transactionCategoryId = "tc1", transactionCategoryName = "Food And Dining"),
        TransactionCategory(transactionCategoryId = "tc2", transactionCategoryName = "Transport"),
        TransactionCategory(transactionCategoryId = "tc3", transactionCategoryName = "Utilities"),
    )

    val expenseCategories = listOf(
        ExpenseCategory(expenseCategoryId = "ec1", expenseCategoryName = "Rent"),
        ExpenseCategory(expenseCategoryId = "ec2", expenseCategoryName = "Groceries"),
        ExpenseCategory(expenseCategoryId = "ec3", expenseCategoryName = "Insurance"),
    )

    val transactions = listOf(
        transaction(id = "t1", name = "Lunch at Java", amount = 850, categoryId = "tc1"),
        transaction(id = "t2", name = "Uber to town", amount = 450, categoryId = "tc2"),
        transaction(id = "t3", name = "Electricity tokens", amount = 2000, categoryId = "tc3"),
    )

    val expenses = listOf(
        expense(id = "e1", name = "October rent", amount = 25000, categoryId = "ec1"),
        expense(id = "e2", name = "Weekly shopping", amount = 6500, categoryId = "ec2"),
        expense(id = "e3", name = "Car insurance", amount = 4200, categoryId = "ec3"),
    )

    val incomes = listOf(
        Income(incomeId = "i1", incomeName = "Salary", incomeAmount = 120000, incomeCreatedAt = "01/10/2026"),
        Income(incomeId = "i2", incomeName = "Freelance project", incomeAmount = 35000, incomeCreatedAt = "05/10/2026"),
    )

    val transaction = transactions.first()
    val expense = expenses.first()
    val income = incomes.first()

    private fun transaction(id: String, name: String, amount: Int, categoryId: String) = Transaction(
        transactionId = id,
        transactionName = name,
        transactionAmount = amount,
        transactionCategoryId = categoryId,
        transactionCreatedAt = "12:30:00",
        transactionCreatedOn = "10/10/2026",
        transactionUpdatedAt = "12:30:00",
        transactionUpdatedOn = "10/10/2026",
    )

    private fun expense(id: String, name: String, amount: Int, categoryId: String) = Expense(
        expenseId = id,
        expenseName = name,
        expenseAmount = amount,
        expenseCategoryId = categoryId,
        expenseCreatedAt = "09:00:00",
        expenseCreatedOn = "10/10/2026",
        expenseUpdatedAt = "09:00:00",
        expenseUpdatedOn = "10/10/2026",
    )
}

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
package com.peterchege.expensetrackerapp.domain

import com.peterchege.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseEntity
import com.peterchege.expensetrackerapp.core.room.entities.IncomeEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.models.Income
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory


fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        transactionId = transactionId,
        transactionName = transactionName,
        transactionAmount = transactionAmount,
        transactionCreatedAt = transactionCreatedAt,
        transactionUpdatedAt = transactionUpdatedAt,
        transactionCategoryId = transactionCategoryId,
        transactionUpdatedOn = transactionUpdatedOn,
        transactionCreatedOn = transactionCreatedOn,
    )
}

fun TransactionEntity.toExternalModel(): Transaction {
    return Transaction(
        transactionId = transactionId,
        transactionName = transactionName,
        transactionAmount = transactionAmount,
        transactionCreatedAt = transactionCreatedAt,
        transactionUpdatedAt = transactionUpdatedAt,
        transactionCategoryId = transactionCategoryId,
        transactionCreatedOn = transactionCreatedOn,
        transactionUpdatedOn = transactionUpdatedOn
    )
}


fun Expense.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        expenseId = expenseId,
        expenseName = expenseName,
        expenseAmount = expenseAmount,
        expenseCategoryId = expenseCategoryId,
        expenseCreatedAt = expenseCreatedAt,
        expenseUpdatedAt = expenseUpdatedAt,
        expenseUpdatedOn = expenseUpdatedOn,
        expenseCreatedOn = expenseCreatedOn,
    )
}

fun ExpenseEntity.toExternalModel(): Expense {
    return Expense(
        expenseId = expenseId,
        expenseName = expenseName,
        expenseAmount = expenseAmount,
        expenseCategoryId = expenseCategoryId,
        expenseCreatedAt = expenseCreatedAt,
        expenseUpdatedAt = expenseUpdatedAt,
        expenseUpdatedOn = expenseUpdatedOn,
        expenseCreatedOn = expenseCreatedOn,
    )
}


fun TransactionCategory.toEntity(): TransactionCategoryEntity {
    return TransactionCategoryEntity(
        transactionCategoryId = transactionCategoryId,
        transactionCategoryName = transactionCategoryName,


        )
}

fun TransactionCategoryEntity.toExternalModel(): TransactionCategory {
    return TransactionCategory(
        transactionCategoryId = transactionCategoryId,
        transactionCategoryName = transactionCategoryName,


        )
}

fun ExpenseCategory.toEntity(): ExpenseCategoryEntity {
    return ExpenseCategoryEntity(
        expenseCategoryId = expenseCategoryId,
        expenseCategoryName = expenseCategoryName,


        )
}

fun ExpenseCategoryEntity.toExternalModel(): ExpenseCategory {
    return ExpenseCategory(
        expenseCategoryId = expenseCategoryId,
        expenseCategoryName = expenseCategoryName,
        )
}

fun Income.toEntity(): IncomeEntity {
    return IncomeEntity(
        incomeId = incomeId,
        incomeName = incomeName,
        incomeAmount = incomeAmount,
        incomeCreatedAt = incomeCreatedAt,

        )
}

fun IncomeEntity.toExternalModel(): Income {
    return Income(
        incomeId = incomeId,
        incomeName = incomeName,
        incomeAmount = incomeAmount,
        incomeCreatedAt = incomeCreatedAt,
    )
}
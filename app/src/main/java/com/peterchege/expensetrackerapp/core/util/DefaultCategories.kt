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
package com.peterchege.expensetrackerapp.core.util

import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import java.util.UUID

data class ExampleTransactionCategory(
    val name:String,
)

data class ExampleExpenseCategory(
    val name:String
)
val sampleTransactionCategories = listOf<ExampleTransactionCategory>(
    ExampleTransactionCategory("Food And Dining"),
    ExampleTransactionCategory("Transport"),
    ExampleTransactionCategory("Entertainment"),
    ExampleTransactionCategory("Personal Care"),
    ExampleTransactionCategory("Utilities"),
    ExampleTransactionCategory("Internet"),
    ExampleTransactionCategory("Online Subscriptions"),
    ExampleTransactionCategory("Clothes"),
    ExampleTransactionCategory("Phone Bill"),
    ExampleTransactionCategory("Debt"),

)

val sampleExpenseCategories = listOf<ExampleExpenseCategory>(
    ExampleExpenseCategory("Food"),
    ExampleExpenseCategory("Internet"),
    ExampleExpenseCategory("Entertainment"),
    ExampleExpenseCategory("Savings"),
    ExampleExpenseCategory("Health and Wellness"),
    ExampleExpenseCategory("Personal Care"),
    ExampleExpenseCategory("Car Expenses"),
    ExampleExpenseCategory("Education"),
    ExampleExpenseCategory("Black Tax"),
    ExampleExpenseCategory("Clothes"),
    ExampleExpenseCategory("Phone Bill"),
    ExampleExpenseCategory("Travel"),

)

fun ExampleTransactionCategory.toTransactionCategory():TransactionCategory{
    return TransactionCategory(
        transactionCategoryName = name,
        transactionCategoryId = UUID.randomUUID().toString(),
    )
}

fun ExampleExpenseCategory.toExpenseCategory():ExpenseCategory {
    return ExpenseCategory(
        expenseCategoryName = name,
        expenseCategoryId = UUID.randomUUID().toString()
    )
}
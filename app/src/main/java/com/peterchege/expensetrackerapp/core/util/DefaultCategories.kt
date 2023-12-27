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
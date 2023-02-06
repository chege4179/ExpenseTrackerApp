package com.peterchege.expensetrackerapp.domain

import com.peterchege.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
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

fun TransactionEntity.toExternalModel():Transaction{
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


fun Expense.toEntity():ExpenseEntity{
    return ExpenseEntity(
        expenseId = expenseId ,
        expenseName = expenseName,
        expenseAmount = expenseAmount,
        expenseCategoryId = expenseCategoryId,
        expenseCreatedAt = expenseCreatedAt ,
        expenseUpdatedAt =expenseUpdatedAt,
        expenseUpdatedOn = expenseUpdatedOn,
        expenseCreatedOn = expenseCreatedOn,
    )
}

fun ExpenseEntity.toExternalModel():Expense{
    return Expense(
        expenseId = expenseId ,
        expenseName = expenseName,
        expenseAmount = expenseAmount,
        expenseCategoryId = expenseCategoryId,
        expenseCreatedAt = expenseCreatedAt ,
        expenseUpdatedAt =expenseUpdatedAt,
        expenseUpdatedOn = expenseUpdatedOn,
        expenseCreatedOn = expenseCreatedOn,
    )
}


fun TransactionCategory.toEntity():TransactionCategoryEntity{
    return TransactionCategoryEntity(
        transactionCategoryId = transactionCategoryId,
        transactionCategoryName = transactionCategoryName,
        transactionCategoryCreatedAt = transactionCategoryCreatedAt,

    )
}

fun TransactionCategoryEntity.toExternalModel():TransactionCategory{
    return TransactionCategory(
        transactionCategoryId = transactionCategoryId,
        transactionCategoryName = transactionCategoryName,
        transactionCategoryCreatedAt = transactionCategoryCreatedAt,

    )
}

fun ExpenseCategory.toEntity():ExpenseCategoryEntity{
    return ExpenseCategoryEntity(
        expenseCategoryId = expenseCategoryId,
        expenseCategoryName = expenseCategoryName,
        expenseCategoryCreatedAt = expenseCategoryCreatedAt,

    )
}

fun ExpenseCategoryEntity.toExternalModel():ExpenseCategory{
    return ExpenseCategory(
        expenseCategoryId = expenseCategoryId,
        expenseCategoryName = expenseCategoryName,
        expenseCategoryCreatedAt = expenseCategoryCreatedAt,

    )
}
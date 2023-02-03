package com.peterchege.expensetrackerapp.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Expenses")
data class ExpenseEntity (
    @PrimaryKey
    val expenseId:String,
    val expenseName:String,
    val expenseAmount:Int,
    val expenseCategoryId:String,

    val expenseCreatedAt:String,
    val expenseCreatedOn:String,

    val expenseUpdatedOn:String,
    val expenseUpdatedAt:String,
        )
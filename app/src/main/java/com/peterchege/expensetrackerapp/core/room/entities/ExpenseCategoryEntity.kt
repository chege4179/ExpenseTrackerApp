package com.peterchege.expensetrackerapp.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExpenseCategory")
data class ExpenseCategoryEntity(
    @PrimaryKey
    val expenseCategoryId:String,
    val expenseCategoryName:String,
    val expenseCategoryCreatedAt:String,
    val expenseCategoryCreatedOn:String,
)
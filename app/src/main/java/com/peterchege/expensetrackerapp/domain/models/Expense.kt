package com.peterchege.expensetrackerapp.domain.models

import java.util.*

data class Expense (
    val expenseId:String,
    val expenseName:String,
    val expenseAmount:Int,
    val expenseCategoryId:String,

    val expenseCreatedAt: String,
    val expenseCreatedOn: String,


    val expenseUpdatedAt:String,
    val expenseUpdatedOn:String,


    )
package com.peterchege.expensetrackerapp.domain.models

import java.util.*


data class TransactionCategory (
    val transactionCategoryId:String,
    val transactionCategoryName:String,
    val transactionCategoryCreatedAt: Date,
)
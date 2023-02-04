package com.peterchege.expensetrackerapp.domain.models

data class Transaction (
    val transactionId :String,
    val transactionName:String,
    val transactionAmount:Int,
    val transactionCategoryId:String,

    val transactionCreatedAt:String,
    val transactionCreatedOn:String,

    val transactionUpdatedAt:String,
    val transactionUpdatedOn:String
        )
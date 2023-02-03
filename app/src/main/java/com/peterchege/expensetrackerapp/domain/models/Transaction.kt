package com.peterchege.expensetrackerapp.domain.models

data class Transaction (
    val transactionName:String,
    val transactionAmount:Int,

    val transactionCreatedAt:String,
    val transactionCreatedOn:String,

    val transactionUpdatedAt:String,
    val transactionUpdatedOn:String
        )
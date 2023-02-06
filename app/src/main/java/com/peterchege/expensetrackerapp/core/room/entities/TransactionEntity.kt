package com.peterchege.expensetrackerapp.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Transactions")
data class TransactionEntity (
    @PrimaryKey
    val transactionId :String,
    val transactionName:String,
    val transactionAmount:Int,
    val transactionCategoryId:String,

    val transactionCreatedAt:String,
    val transactionCreatedOn:String,

    val transactionUpdatedAt: String,
    val transactionUpdatedOn: String,

    )
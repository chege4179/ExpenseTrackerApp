package com.peterchege.expensetrackerapp.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "TransactionCategory")
data class TransactionCategoryEntity(
    @PrimaryKey
    val transactionCategoryId:String,
    val transactionCategoryName:String,
    val transactionCategoryCreatedAt:Date,
)
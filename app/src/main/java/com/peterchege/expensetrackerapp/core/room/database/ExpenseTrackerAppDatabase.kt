package com.peterchege.expensetrackerapp.core.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.peterchege.expensetrackerapp.core.room.dao.ExpenseCategoryEntityDao
import com.peterchege.expensetrackerapp.core.room.dao.ExpenseEntityDao
import com.peterchege.expensetrackerapp.core.room.dao.TransactionCategoryEntityDao
import com.peterchege.expensetrackerapp.core.room.dao.TransactionEntityDao
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.core.room.type_converters.DateConverter

@TypeConverters(DateConverter::class)
@Database(
    entities = [
        ExpenseCategoryEntity::class,
        TransactionCategoryEntity::class,
        ExpenseEntity::class,
        TransactionEntity::class,
    ],
    version = 1
)
abstract class ExpenseTrackerAppDatabase : RoomDatabase() {


    abstract val expenseCategoryEntityDao: ExpenseCategoryEntityDao

    abstract val transactionCategoryEntityDao: TransactionCategoryEntityDao

    abstract val expenseEntityDao:ExpenseEntityDao

    abstract val transactionEntityDao:TransactionEntityDao

}
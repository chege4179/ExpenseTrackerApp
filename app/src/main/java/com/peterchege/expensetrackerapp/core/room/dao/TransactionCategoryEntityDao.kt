package com.peterchege.expensetrackerapp.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.peterchege.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionCategoryEntityDao {

    @Query("SELECT * FROM TransactionCategory")
    fun getTransactionCategories(): Flow<List<TransactionCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactionCategory(transactionCategoryEntity: TransactionCategoryEntity)

    @Query("DELETE FROM TransactionCategory WHERE transactionCategoryId = :id")
    suspend fun deleteTransactionCategoryById(id: String)

    @Query("DELETE FROM TransactionCategory")
    suspend fun deleteAllTransactionCategories()

}
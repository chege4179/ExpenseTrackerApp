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

    @Query("SELECT * FROM TransactionCategory WHERE transactionCategoryName =:name")
    suspend fun getTransactionCategoryByName(name:String):TransactionCategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactionCategory(transactionCategoryEntity: TransactionCategoryEntity)


    @Query("DELETE FROM TransactionCategory WHERE transactionCategoryId = :id")
    suspend fun deleteTransactionCategoryById(id: String)

    @Query("DELETE FROM TransactionCategory")
    suspend fun deleteAllTransactionCategories()

    @Query("UPDATE TransactionCategory SET transactionCategoryName = :name WHERE transactionCategoryId = :id")
    suspend fun updateExpenseCategoryName(id:String,name:String)

}
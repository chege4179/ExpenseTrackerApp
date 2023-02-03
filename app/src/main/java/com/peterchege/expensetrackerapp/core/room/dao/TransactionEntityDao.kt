package com.peterchege.expensetrackerapp.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionEntityDao {

    @Query("SELECT * FROM Transactions")
    fun getTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity)

    @Query("DELETE FROM Transactions WHERE transactionId = :id")
    suspend fun deleteTransactionById(id: String)

    @Query("DELETE FROM Transactions")
    suspend fun deleteAllTransactions()

}
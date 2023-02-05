package com.peterchege.expensetrackerapp.data

import com.peterchege.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import com.peterchege.expensetrackerapp.domain.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val db: ExpenseTrackerAppDatabase,
) : TransactionRepository {
    override suspend fun createTransaction(transaction: Transaction) {
        return db.transactionEntityDao.insertTransaction(
            transactionEntity = transaction.toEntity()
        )
    }

    override fun getAllTransactions(): Flow<List<TransactionEntity>> {
        return db.transactionEntityDao.getTransactions()

    }

}
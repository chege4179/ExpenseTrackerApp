package com.peterchege.expensetrackerapp.data

import com.peterchege.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.peterchege.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import com.peterchege.expensetrackerapp.domain.repository.TransactionCategoryRepository
import com.peterchege.expensetrackerapp.domain.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionCategoryRepositoryImpl @Inject constructor(
    private val db:ExpenseTrackerAppDatabase

) :TransactionCategoryRepository{
    override suspend fun saveTransactionCategory(transactionCategory: TransactionCategory) {
        return db.transactionCategoryEntityDao.insertTransactionCategory(
            transactionCategoryEntity = transactionCategory.toEntity())
    }

    override fun getAllTransactionCategories(): Flow<List<TransactionCategoryEntity>> {
        return db.transactionCategoryEntityDao.getTransactionCategories()
    }

    override suspend fun getTransactionCategoryByName(name: String): TransactionCategoryEntity? {
        return db.transactionCategoryEntityDao.getTransactionCategoryByName(name = name)
    }

    override suspend fun updateTransactionCategory(
        transactionCategoryId: String,
        transactionCategoryName: String
    ) {
        return db.transactionCategoryEntityDao.updateExpenseCategoryName(
            id = transactionCategoryId,
            name = transactionCategoryName,
        )

    }

    override suspend fun deleteTransactionCategory(transactionCategoryId: String) {
        return db.transactionCategoryEntityDao.deleteTransactionCategoryById(
            id = transactionCategoryId)
    }
}
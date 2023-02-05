package com.peterchege.expensetrackerapp.domain.use_case

import com.peterchege.expensetrackerapp.core.room.entities.TransactionCategoryEntity
import com.peterchege.expensetrackerapp.domain.repository.TransactionCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTransactionCategoriesUseCase @Inject constructor(
    private val repository: TransactionCategoryRepository
){

    operator fun invoke(): Flow<List<TransactionCategoryEntity>> {
        return repository.getAllTransactionCategories()
    }
}
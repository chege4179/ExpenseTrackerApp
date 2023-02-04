package com.peterchege.expensetrackerapp.domain.use_case

import com.peterchege.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import com.peterchege.expensetrackerapp.core.util.Resource
import com.peterchege.expensetrackerapp.domain.repository.ExpenseCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class GetAllExpenseCategoriesUseCase @Inject constructor(
    private val repository: ExpenseCategoryRepository
) {

    operator fun invoke():Flow<List<ExpenseCategoryEntity>>{
        return repository.getAllExpenseCategories()
    }
}
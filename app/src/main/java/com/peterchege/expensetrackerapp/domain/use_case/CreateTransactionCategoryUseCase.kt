package com.peterchege.expensetrackerapp.domain.use_case

import com.peterchege.expensetrackerapp.core.util.Resource
import com.peterchege.expensetrackerapp.core.util.Response
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import com.peterchege.expensetrackerapp.domain.repository.TransactionCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class CreateTransactionCategoryUseCase @Inject constructor(
    private val repository: TransactionCategoryRepository
){

    suspend operator fun invoke(transactionCategory:TransactionCategory): Flow<Resource<Response>> = flow {
        try {
            emit(Resource.Loading())
            val existingTransactionCategory = repository.getTransactionCategoryByName(
                name = transactionCategory.transactionCategoryName)
            if (existingTransactionCategory == null){
                repository.saveTransactionCategory(transactionCategory = transactionCategory)
                emit(Resource.Success(Response(msg = "Transaction Category Added")))
            }else{
                emit(Resource.Error(message = "A Transaction category with a similar name already exists"))
            }
        }catch (e:IOException){
            emit(Resource.Error(message = e.localizedMessage?: "An unexpected error occurred"))
        }
    }
}
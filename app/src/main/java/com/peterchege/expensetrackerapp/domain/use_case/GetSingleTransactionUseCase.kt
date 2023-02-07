package com.peterchege.expensetrackerapp.domain.use_case

import android.view.SurfaceControl.Transaction
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import com.peterchege.expensetrackerapp.domain.repository.TransactionCategoryRepository
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import com.peterchege.expensetrackerapp.domain.toExternalModel
import javax.inject.Inject

data class TransactionInfo(
    val transaction:Transaction?,
    val category:TransactionCategory?,
)
class GetSingleTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val transactionCategoryRepository: TransactionCategoryRepository
){

    operator suspend fun invoke(transactionId:String):TransactionInfo?{
        val transaction = transactionRepository.getTransactionById(transactionId = transactionId)
        val transactionCategory = transactionCategoryRepository.getTransactionCategoryById(
            transactionId = transaction.transactionCategoryId)
        return TransactionInfo(
            transaction = transaction.toExternalModel(),
            category = transactionCategory.toExternalModel(),
        )

    }
}
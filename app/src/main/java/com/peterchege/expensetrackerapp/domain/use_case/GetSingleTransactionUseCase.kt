/*
 * Copyright 2023 Expense Tracker App By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
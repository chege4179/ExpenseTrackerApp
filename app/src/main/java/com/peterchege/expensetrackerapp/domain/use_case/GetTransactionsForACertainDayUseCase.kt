package com.peterchege.expensetrackerapp.domain.use_case

import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsForACertainDayUseCase @Inject constructor(
    private val repository:TransactionRepository
) {

    operator fun invoke(date:String): Flow<List<TransactionEntity>> {
        return repository.getTransactionsForACertainDay(date = date)

    }
}
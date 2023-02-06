package com.peterchege.expensetrackerapp.domain.use_case

import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.core.util.generateFormatDate
import com.peterchege.expensetrackerapp.core.util.previousDay
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject


class GetFilteredTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    val todayDate = generateFormatDate(date = LocalDate.now())
    val yesterdayDate = previousDay(date = todayDate)

    operator fun invoke(filter:String): Flow<List<TransactionEntity>> {
        if (filter == FilterConstants.TODAY){
            return repository.getTransactionsForACertainDay(date = todayDate)
        }else if (filter == FilterConstants.YESTERDAY){
            return repository.getTransactionsForACertainDay(date = yesterdayDate)
        }else{
            return repository.getAllTransactions()
        }
    }
}
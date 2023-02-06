package com.peterchege.expensetrackerapp.domain.use_case

import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.core.util.*
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.Locale.filter
import javax.inject.Inject


class GetFilteredTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    val todayDate = generateFormatDate(date = LocalDate.now())
    val yesterdayDate = previousDay(date = todayDate)
    val weekDates = getWeekDates(dateString = todayDate)
    val monthDates = getMonthDates(dateString =  todayDate)
    val oneWeekEarlierDate = generate7daysPriorDate(date = todayDate)
    val daysInThe7daysPrior = datesBetween(startDate = oneWeekEarlierDate, endDate = todayDate)

    operator fun invoke(filter:String): Flow<List<TransactionEntity>> {
        if (filter == FilterConstants.TODAY){
            return repository.getTransactionsForACertainDay(date = todayDate)
        }else if (filter == FilterConstants.YESTERDAY){
            return repository.getTransactionsForACertainDay(date = yesterdayDate)
        }else if(filter == FilterConstants.THIS_WEEK){
            return repository.getTransactionsBetweenTwoDates(dates = weekDates)
        }else if (filter == FilterConstants.THIS_MONTH){
            return repository.getTransactionsBetweenTwoDates(dates = monthDates)
        }else if (filter == FilterConstants.LAST_7_DAYS){
            return repository.getTransactionsBetweenTwoDates(dates = daysInThe7daysPrior)
        }else {
            return repository.getAllTransactions()
        }
    }
}
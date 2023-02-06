package com.peterchege.expensetrackerapp.presentation.screens.analytics_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.core.util.generateFormatDate
import com.peterchege.expensetrackerapp.core.util.getWeekDates
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.use_case.GetTransactionsForACertainDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class GraphItem(
    val total:Int,
    val transactions:List<Transaction>
)
@HiltViewModel
class AnalyticsScreenViewModel @Inject constructor(
    private val getTransactionsForACertainDayUseCase: GetTransactionsForACertainDayUseCase,


) : ViewModel(){
    val todayDate = generateFormatDate(LocalDate.now())
    val weekDates = getWeekDates(dateString = todayDate)

    val _graphData = mutableStateOf<List<GraphDataItem>>(emptyList())
    val graphData : State<List<GraphDataItem>> = _graphData

    data class GraphDataItem(
        val transactions: Flow<List<TransactionEntity>>,
    )



    init {
        viewModelScope.launch {
            val defferedFlows = weekDates.map { async { getTransactionsForACertainDayUseCase(date = it) } }
            val results = defferedFlows.awaitAll()
            val newResults = results.map { GraphDataItem(transactions = it)}
            _graphData.value = newResults
        }
    }







}
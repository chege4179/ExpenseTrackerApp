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
package com.peterchege.expensetrackerapp.presentation.screens.analytics_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.charts.bar.BarChartData

import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.core.util.*
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
    val sevendaysBefore = generate7daysPriorDate(todayDate)
    val sevenDaysBeforeDates = datesBetween(sevendaysBefore,todayDate)
    val monthDates = getMonthDates(dateString = todayDate)



    val _graphData = mutableStateOf<List<Flow<List<TransactionEntity>>>>(emptyList())
    val graphData : State<List<Flow<List<TransactionEntity>>>> = _graphData

    val _barDataList = mutableStateOf<List<BarChartData.Bar>>(emptyList())
    val barDataList:State<List<BarChartData.Bar>> = _barDataList

    val _activeFilterConstant = mutableStateOf(FilterConstants.THIS_WEEK)
    val activeFilterConstant:State<String> = _activeFilterConstant


    fun onChangeBarDataList(data:List<BarChartData.Bar>){
        _barDataList.value = data
    }

    fun onChangeActiveFilterConstant(filter:String){
        _activeFilterConstant.value = filter
        if (filter == FilterConstants.LAST_7_DAYS){
            getGraphData(dates = sevenDaysBeforeDates)

        }else if (filter == FilterConstants.THIS_MONTH){
            getGraphData(dates = monthDates)
        }else if (filter == FilterConstants.THIS_WEEK){
            getGraphData(dates = weekDates)
        }
    }



    init {
        getGraphData(dates = weekDates)
    }

    private fun getGraphData(dates:List<String>){
        viewModelScope.launch {
            val data = dates.map {
                getTransactionsForACertainDayUseCase(date = it)
            }
            _graphData.value = data
        }
    }







}
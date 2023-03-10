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
package com.peterchege.expensetrackerapp.presentation.screens.search_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.core.util.datesBetween
import com.peterchege.expensetrackerapp.core.util.generateFormatDate
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.domain.use_case.GetAllTransactionCategoriesUseCase
import com.peterchege.expensetrackerapp.domain.use_case.SearchTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getAllTransactionCategoriesUseCase: GetAllTransactionCategoriesUseCase,
    private val searchTransactionsUseCase: SearchTransactionsUseCase,

    ) :ViewModel(){
    val transactionCategories = getAllTransactionCategoriesUseCase()


    val _selectedIndex = mutableStateOf(0)
    val selectedIndex: State<Int> = _selectedIndex

    val _selectedTransactionCategory = mutableStateOf<TransactionCategory?>(null)
    val selectedTransactionCategory: State<TransactionCategory?> = _selectedTransactionCategory

    val _transactions = mutableStateOf<List<Transaction>>(emptyList())
    val transactions: State<List<Transaction>> = _transactions


    val _transactionStartDate = mutableStateOf<LocalDate?>(null)
    val transactionStartDate: State<LocalDate?> = _transactionStartDate

    val _transactionEndDate = mutableStateOf<LocalDate?>(null)
    val transactionEndDate: State<LocalDate?> = _transactionEndDate


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeTransactionStartDate(date: LocalDate) {
        _transactionStartDate.value = date
    }

    fun onChangeTransactionEndDate(date: LocalDate) {
        _transactionEndDate.value = date
    }

    fun onChangeSelectedTransactionCategoryIndex(index:Int){
        _selectedIndex.value = index
    }

    fun onChangeSelectedTransactionCategory(category: TransactionCategory) {
        _selectedTransactionCategory.value = category

    }

    fun searchTransactions(){
        viewModelScope.launch {
            if (_transactionEndDate.value == null){
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Please select an end date"))
                return@launch
            }
            if (_transactionStartDate.value == null){
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Please select a start date"))
                return@launch
            }
            if (_selectedTransactionCategory.value == null){
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Please select a transaction category"))
                return@launch
            }
            val datesInBetween = datesBetween(
                startDate = generateFormatDate(_transactionStartDate.value!!) ,
                endDate = generateFormatDate(_transactionEndDate.value!!)
            )
            searchTransactionsUseCase(
                dates = datesInBetween,
                categoryId = _selectedTransactionCategory.value!!.transactionCategoryId)
                .collect{ transactions ->
                    _transactions.value = transactions.map { it.toExternalModel() }
                }
        }
    }




}
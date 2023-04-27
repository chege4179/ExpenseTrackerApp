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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

data class SearchScreenState(
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val transactionCategory: TransactionCategory? = null,
    val transactions: List<Transaction> = emptyList(),

    )


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getAllTransactionCategoriesUseCase: GetAllTransactionCategoriesUseCase,
    private val searchTransactionsUseCase: SearchTransactionsUseCase,

    ) : ViewModel() {
    val transactionCategories = getAllTransactionCategoriesUseCase()

    private val _uiState = MutableStateFlow(SearchScreenState())
    val uiState = _uiState.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeTransactionStartDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(startDate = date)
    }

    fun onChangeTransactionEndDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(endDate = date)
    }

    fun onChangeTransactionCategory(category: TransactionCategory) {
        _uiState.value = _uiState.value.copy(transactionCategory = category)
    }


    fun searchTransactions() {
        viewModelScope.launch {

            if (_uiState.value.transactionCategory == null) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Please select a transaction category"))
                return@launch
            }
            val datesInBetween = datesBetween(
                startDate = generateFormatDate(_uiState.value.startDate),
                endDate = generateFormatDate(_uiState.value.endDate)
            )
            searchTransactionsUseCase(
                dates = datesInBetween,
                categoryId = _uiState.value.transactionCategory!!.transactionCategoryId
            ).collect { transactions ->
                    _uiState.value = _uiState.value.copy(transactions =
                    transactions.map { it.toExternalModel() })

                }
        }
    }


}
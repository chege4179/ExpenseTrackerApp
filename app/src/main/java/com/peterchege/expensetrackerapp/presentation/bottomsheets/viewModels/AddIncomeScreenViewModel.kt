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
package com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.core.util.Resource
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.core.util.generateFormatDate
import com.peterchege.expensetrackerapp.core.util.isNumeric
import com.peterchege.expensetrackerapp.core.util.localDateTimeToDate
import com.peterchege.expensetrackerapp.domain.models.Income
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.use_case.CreateIncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddIncomeScreenViewModel @Inject constructor(
    private val createIncomeUseCase: CreateIncomeUseCase,
) :ViewModel() {

    val _incomeName = mutableStateOf("")
    val incomeName: State<String> = _incomeName

    val _incomeAmount = mutableStateOf(0)
    val incomeAmount: State<Int> = _incomeAmount

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeIncomeAmount(text: String) {
        if (text.isBlank()) {
            _incomeAmount.value = 0
            return
        }
        if (isNumeric(text)) {
            _incomeAmount.value = text.toInt()

        } else {
            _incomeAmount.value = 0
        }
    }

    fun onChangeIncomeName(text: String) {
        _incomeName.value = text

    }

    fun addIncome() {
        viewModelScope.launch {
            if (_incomeName.value.isBlank()) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Income Name cannot be black"))
                return@launch
            }
            if (_incomeAmount.value == 0 ) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Income Amount cannot be 0"))
                return@launch
            }
            val income = Income(
                incomeName= _incomeName.value,
                incomeAmount = _incomeAmount.value,
                incomeId = UUID.randomUUID().toString(),
                incomeCreatedAt = generateFormatDate(date = LocalDate.now()) ,
            )
            createIncomeUseCase(income = income).onEach { result ->
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {

                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = result.data?.msg ?: "Transaction added successfully"
                            )
                        )
                        _incomeAmount.value = 0
                        _incomeName.value = ""
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = result.message
                                    ?: "An error occurred creating this transaction"
                            )
                        )
                    }
                }
            }.launchIn(this)
        }

    }
}
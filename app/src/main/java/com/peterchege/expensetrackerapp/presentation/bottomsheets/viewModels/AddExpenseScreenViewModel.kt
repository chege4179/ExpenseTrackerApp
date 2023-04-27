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
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.core.util.Resource
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.core.util.generateFormatDate
import com.peterchege.expensetrackerapp.core.util.isNumeric
import com.peterchege.expensetrackerapp.core.util.localDateTimeToDate
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.use_case.CreateExpenseUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetAllExpenseCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.inject.Inject

data class AddExpenseFormState(
    val expenseName: String = "",
    val expenseAmount: Int = 0,
    val selectedExpenseCategory: ExpenseCategory? = null,
    val isLoading: Boolean = false,
    )
@HiltViewModel
class AddExpenseScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAllExpenseCategoriesUseCase: GetAllExpenseCategoriesUseCase,
    private val createExpenseUseCase: CreateExpenseUseCase,

    ) : ViewModel() {

    val _formState = MutableStateFlow(AddExpenseFormState())
    val formState = _formState.asStateFlow()

    val expenseCategories = getAllExpenseCategoriesUseCase()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeExpenseName(text: String) {
        _formState.value = _formState.value.copy(expenseName = text)
    }

    fun onChangeExpenseAmount(text: String) {
        if (text.isBlank()) {
            _formState.value = _formState.value.copy(expenseAmount = 0)

            return
        }
        if (isNumeric(text)) {
            _formState.value = _formState.value.copy(expenseAmount = text.toInt())


        } else {

            _formState.value = _formState.value.copy(expenseAmount = 0)
        }
    }

    fun onChangeSelectedExpenseCategory(category: ExpenseCategory) {
        _formState.value = _formState.value.copy(selectedExpenseCategory = category)
    }

    fun addExpense() {
        viewModelScope.launch {
            if (_formState.value.expenseAmount == 0 || _formState.value.expenseName == "") {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        uiText = "Please enter a valid expense"
                    )
                )
                return@launch
            }
            if (_formState.value.selectedExpenseCategory == null) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        uiText = "Please select an expense category"
                    )
                )
                return@launch
            }
            val newExpense = Expense(
                expenseId = UUID.randomUUID().toString(),
                expenseName = _formState.value.expenseName,
                expenseAmount = _formState.value.expenseAmount,
                expenseCategoryId = _formState.value.selectedExpenseCategory!!.expenseCategoryId,
                expenseCreatedAt = SimpleDateFormat("hh:mm:ss").format(Date()),
                expenseUpdatedAt = SimpleDateFormat("hh:mm:ss").format(Date()),
                expenseCreatedOn = generateFormatDate(date = LocalDate.now()),
                expenseUpdatedOn = generateFormatDate(date = LocalDate.now())
            )
            createExpenseUseCase(expense = newExpense).onEach { result ->
                when (result) {
                    is Resource.Loading -> {

                        _formState.value = _formState.value.copy(isLoading = true)

                    }

                    is Resource.Success -> {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = result.data?.msg ?: "Expense  added successfully"
                            )
                        )
                        _formState.value = _formState.value.copy(
                            expenseName = "",
                            expenseAmount = 0,
                            selectedExpenseCategory = null,
                            isLoading = false,
                        )
                    }

                    is Resource.Error -> {
                        _formState.value = _formState.value.copy(isLoading = false)
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = result.message ?: "An error occurred"
                            )
                        )

                    }
                }
            }.launchIn(this)
        }
    }
}
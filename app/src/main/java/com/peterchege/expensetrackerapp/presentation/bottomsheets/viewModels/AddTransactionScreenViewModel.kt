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
import com.peterchege.expensetrackerapp.core.util.*
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import com.peterchege.expensetrackerapp.domain.use_case.CreateTransactionUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetAllTransactionCategoriesUseCase
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


data class AddTransactionFormState(
    val transactionName:String = "",
    val transactionAmount:Int = 0,
    val transactionTime:LocalTime = LocalTime.now(),
    val transactionDate:LocalDate = LocalDate.now(),
    val transactionCategory:TransactionCategory? = null,
    val isLoading:Boolean = false,
)
@HiltViewModel
class AddTransactionScreenViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getAllTransactionCategoriesUseCase: GetAllTransactionCategoriesUseCase,
    ) : ViewModel() {

    val transactionCategories = getAllTransactionCategoriesUseCase()

    private val _formState = MutableStateFlow(AddTransactionFormState())
    val formState = _formState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeTransactionDate(date: LocalDate) {
        _formState.value = _formState.value.copy(transactionDate = date)

    }

    fun onChangeTransactionTime(time: LocalTime) {
        _formState.value = _formState.value.copy(transactionTime = time)
    }

    fun onChangeTransactionName(text: String) {
        _formState.value = _formState.value.copy(transactionName = text)

    }
    fun onChangeSelectedTransactionCategory(category: TransactionCategory) {
        _formState.value = _formState.value.copy(transactionCategory = category)

    }

    fun onChangeTransactionAmount(text: String) {
        if (text.isBlank()) {
            _formState.value = _formState.value.copy(transactionAmount = 0)
            return
        }
        if (isNumeric(text)) {
            _formState.value = _formState.value.copy(transactionAmount = text.toInt())


        } else {
            _formState.value = _formState.value.copy(transactionAmount = 0)
        }
    }


    fun addTransaction() {
        viewModelScope.launch {
            if (_formState.value.transactionName.isBlank()) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Transaction Name cannot be black"))
                return@launch
            }
            if (_formState.value.transactionCategory == null) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Please select a transaction Category"))
                return@launch
            }
            val date = localDateTimeToDate(
                localDate = _formState.value.transactionDate,
                localTime = _formState.value.transactionTime
            )

            val transaction = Transaction(
                transactionName = _formState.value.transactionName,
                transactionAmount = _formState.value.transactionAmount,
                transactionCategoryId = _formState.value.transactionCategory!!.transactionCategoryId,
                transactionId = UUID.randomUUID().toString(),
                transactionCreatedAt = SimpleDateFormat("hh:mm:ss").format(date) ,
                transactionUpdatedAt = SimpleDateFormat("hh:mm:ss").format(date),
                transactionUpdatedOn = generateFormatDate(date = _formState.value.transactionDate),
                transactionCreatedOn = generateFormatDate(date = _formState.value.transactionDate),



            )
            createTransactionUseCase(transaction = transaction).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _formState.value = _formState.value.copy(isLoading = true)

                    }
                    is Resource.Success -> {

                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = result.data?.msg ?: "Transaction added successfully"
                            )
                        )
                        _formState.value = AddTransactionFormState()
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
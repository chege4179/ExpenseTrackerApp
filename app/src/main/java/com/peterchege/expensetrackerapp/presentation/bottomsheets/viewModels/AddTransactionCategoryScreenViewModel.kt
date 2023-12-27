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
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import com.peterchege.expensetrackerapp.domain.use_case.CreateTransactionCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class AddTransactionCategoryFormState(
    val transactionCategoryName: String = "",
    val isLoading: Boolean = false
)


@HiltViewModel
class AddTransactionCategoryScreenViewModel @Inject constructor(
    private val createTransactionCategoryUseCase: CreateTransactionCategoryUseCase,


    ) : ViewModel() {

    private val _formState = MutableStateFlow(AddTransactionCategoryFormState())
    val formState = _formState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeTransactionCategoryName(text: String) {
        _formState.value = _formState.value.copy(transactionCategoryName = text)

    }


    fun addTransactionCategory() {
        viewModelScope.launch {
            if (_formState.value.transactionCategoryName.isBlank()) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Transaction Category Name cannot be black"))
            } else {
                val transactionCategory = TransactionCategory(
                    transactionCategoryName = _formState.value.transactionCategoryName,
                    transactionCategoryId = UUID.randomUUID().toString(),

                )
                createTransactionCategoryUseCase(transactionCategory = transactionCategory).onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _formState.value = _formState.value.copy(isLoading = true)

                        }

                        is Resource.Success -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    uiText = result.data?.msg
                                        ?: "Transaction Category added successfully"
                                )
                            )
                            _formState.value = _formState.value.copy(
                                isLoading = false,
                                transactionCategoryName = ""
                            )
                        }

                        is Resource.Error -> {

                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    uiText = result.message ?: "An error occurred"
                                )
                            )
                            _formState.value = _formState.value.copy(
                                isLoading = false,
                            )
                        }
                    }
                }.launchIn(this)


            }
        }

    }


}
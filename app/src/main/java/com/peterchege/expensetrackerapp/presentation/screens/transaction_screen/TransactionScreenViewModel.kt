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
package com.peterchege.expensetrackerapp.presentation.screens.transaction_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.domain.models.TransactionInfo
import com.peterchege.expensetrackerapp.domain.use_case.DeleteTransactionUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetSingleTransactionUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSingleTransactionUseCase: GetSingleTransactionUseCase,
    private val deleteTransactionUseCase:DeleteTransactionUseCase,

) : ViewModel(){
    val _transactionInfo = mutableStateOf<TransactionInfo?>(null)
    val transactionInfo: State<TransactionInfo?> = _transactionInfo

    init {
        savedStateHandle.get<String>("id")?.let {
            viewModelScope.launch {
                val info = getSingleTransactionUseCase(transactionId = it)
                _transactionInfo.value = info
            }
        }
    }

    fun deleteTransaction(){
        viewModelScope.launch {
            _transactionInfo.value?.transaction?.let{
                deleteTransactionUseCase(transactionId = it.transactionId)

            }
        }

    }

}
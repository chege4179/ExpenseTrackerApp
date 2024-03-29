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
package com.peterchege.expensetrackerapp.presentation.screens.transaction

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.models.TransactionInfo
import com.peterchege.expensetrackerapp.domain.use_case.DeleteTransactionUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetSingleTransactionUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DeleteTransactionUiState(
    val isDeleteTransactionDialogVisible:Boolean = false,
    val deleteTransaction: Transaction? = null
)

sealed interface TransactionScreenUiState {
    object Loading:TransactionScreenUiState

    data class Success(val transactionInfo:TransactionInfo):TransactionScreenUiState

    data class Error(val message:String):TransactionScreenUiState
}
@HiltViewModel
class TransactionScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSingleTransactionUseCase: GetSingleTransactionUseCase,
    private val deleteTransactionUseCase:DeleteTransactionUseCase,

) : ViewModel(){

    val transactionId = savedStateHandle.get<String>("id")

    private val _deleteTransactionUiState = MutableStateFlow(DeleteTransactionUiState())
    val deleteTransactionUiState = _deleteTransactionUiState.asStateFlow()

    private val _uiState = MutableStateFlow<TransactionScreenUiState>(TransactionScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()


    init {

        viewModelScope.launch {
            if (transactionId == null){
                _uiState.value = TransactionScreenUiState.Error(message = "Transaction Not Found")
            }else{
                val info = getSingleTransactionUseCase(transactionId = transactionId)
                if (info == null){
                    _uiState.value = TransactionScreenUiState.Error(message = "Transaction Not Found")
                }else{
                    _uiState.value = TransactionScreenUiState.Success(transactionInfo = info)
                }
            }
        }
    }
    fun toggleDeleteDialogVisibility(){
        val initialState = _deleteTransactionUiState.value.isDeleteTransactionDialogVisible
        _deleteTransactionUiState.update {
            it.copy(isDeleteTransactionDialogVisible = !initialState)
        }
    }
    fun setDeleteTransaction(transaction: Transaction?){
        _deleteTransactionUiState.update {
            it.copy(deleteTransaction = transaction)
        }
    }

    fun deleteTransaction(navigateBack:() -> Unit){
        viewModelScope.launch {
            if (_uiState.value is TransactionScreenUiState.Success){
                if (transactionId != null) {
                    deleteTransactionUseCase(transactionId = transactionId)
                    toggleDeleteDialogVisibility()
                    setDeleteTransaction(null)
                    navigateBack()
                }
            }
        }
    }
}
/*
 * Copyright 2026 Expense Tracker App By Peter Chege
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
package com.peterchege.expensetrackerapp.presentation.screens.expense

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.use_case.DeleteExpenseByIdUseCase
import com.peterchege.expensetrackerapp.domain.use_case.ExpenseInfo
import com.peterchege.expensetrackerapp.domain.use_case.GetSingleExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface ExpenseScreenUiState {
    object Loading:ExpenseScreenUiState

    data class Success(val expenseInfo: ExpenseInfo):ExpenseScreenUiState

    data class Error(val message:String):ExpenseScreenUiState

}

data class DeleteExpenseUiState(
    val isDeleteExpenseDialogVisible:Boolean = true,
    val deleteExpense: Expense? = null
)


@HiltViewModel
class ExpenseScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSingleExpenseUseCase: GetSingleExpenseUseCase,
    private val deleteExpenseByIdUseCase: DeleteExpenseByIdUseCase,

):ViewModel(){

    val expenseId = savedStateHandle.get<String>("id")

    private val _deleteExpenseUiState = MutableStateFlow(DeleteExpenseUiState())
    val deleteExpenseUiState = _deleteExpenseUiState.asStateFlow()

    private val _uiState = MutableStateFlow<ExpenseScreenUiState>(ExpenseScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            if (expenseId == null){
                _uiState.value = ExpenseScreenUiState.Error(message = "Expense Not Found")
            }else{
                val info = getSingleExpenseUseCase(expenseId = expenseId)
                if (info == null){
                    _uiState.value = ExpenseScreenUiState.Error(message = "Expense Not Found")
                }else{
                    _uiState.value = ExpenseScreenUiState.Success(expenseInfo = info)
                }
            }
        }
    }
    fun deleteExpense(navigateBack:() -> Unit){
        viewModelScope.launch {
            if (expenseId != null) {
                deleteExpenseByIdUseCase(expenseId)
                toggleDeleteExpenseDialogVisibility()
                navigateBack()
            }
        }
    }

    fun toggleDeleteExpenseDialogVisibility(){
        val initialState = _deleteExpenseUiState.value.isDeleteExpenseDialogVisible
        _deleteExpenseUiState.update {
            it.copy(isDeleteExpenseDialogVisible = !initialState)
        }
    }
    fun setDeleteExpense(expense:Expense?){
        _deleteExpenseUiState.update { it.copy(deleteExpense = expense) }
    }
}
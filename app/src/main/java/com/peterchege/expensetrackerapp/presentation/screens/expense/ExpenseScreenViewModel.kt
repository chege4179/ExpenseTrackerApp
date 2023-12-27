package com.peterchege.expensetrackerapp.presentation.screens.expense

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.domain.use_case.ExpenseInfo
import com.peterchege.expensetrackerapp.domain.use_case.GetSingleExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface ExpenseScreenUiState {
    object Loading:ExpenseScreenUiState

    data class Success(val expenseInfo: ExpenseInfo):ExpenseScreenUiState

    data class Error(val message:String):ExpenseScreenUiState


}



@HiltViewModel
class ExpenseScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSingleExpenseUseCase: GetSingleExpenseUseCase,

):ViewModel(){

    val expenseId = savedStateHandle.get<String>("id")

    private val _uiState = MutableStateFlow<ExpenseScreenUiState>(ExpenseScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            if (expenseId == null){
                _uiState.value = ExpenseScreenUiState.Error(message = "Expense Not Found")
            }else{
                val info = getSingleExpenseUseCase(expenseId = expenseId)
                if (info == null){
                    _uiState.value = ExpenseScreenUiState.Error(message = "Transaction Not Found")
                }else{
                    _uiState.value = ExpenseScreenUiState.Success(expenseInfo = info)
                }
            }
        }
    }

}
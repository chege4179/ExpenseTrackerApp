package com.peterchege.expensetrackerapp.presentation.screens.add_expense_category_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.core.util.Resource
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.use_case.CreateExpenseCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class AddExpenseCategoryScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val createExpenseCategoryUseCase: CreateExpenseCategoryUseCase,
) : ViewModel() {

    val _expenseCategoryName = mutableStateOf("")
    val expenseCategoryName: State<String> = _expenseCategoryName


    val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> =_isLoading



    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeExpenseName(text: String) {
        _expenseCategoryName.value = text

    }


    fun addExpenseCategory() {
        viewModelScope.launch {
            if (_expenseCategoryName.value.isBlank()) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Expense Category Name cannot be black"))
            } else {
                val expenseCategory = ExpenseCategory(
                    expenseCategoryName = _expenseCategoryName.value,
                    expenseCategoryId = UUID.randomUUID().toString(),
                    expenseCategoryCreatedAt = SimpleDateFormat("hh:mm:ss").format(Date()),
                    expenseCategoryCreatedOn = SimpleDateFormat("dd/MM/yyyy").format(Date()),
                )
                createExpenseCategoryUseCase(expenseCategory = expenseCategory).onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _isLoading.value = true

                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            _eventFlow.emit(UiEvent.ShowSnackbar(
                                uiText = result.data?.msg ?: "Expense Category added successfully"))
                            _expenseCategoryName.value = ""
                        }
                        is Resource.Error ->{
                            _isLoading.value = false
                            _eventFlow.emit(UiEvent.ShowSnackbar(
                                uiText = result.message ?:"An error occurred"))

                        }
                    }
                }.launchIn(this)


            }
        }

    }


}
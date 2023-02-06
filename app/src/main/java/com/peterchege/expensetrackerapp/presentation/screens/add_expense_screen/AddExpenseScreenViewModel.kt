package com.peterchege.expensetrackerapp.presentation.screens.add_expense_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.core.util.Resource
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.core.util.isNumeric
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.use_case.CreateExpenseUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetAllExpenseCategoriesUseCase
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
class AddExpenseScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAllExpenseCategoriesUseCase: GetAllExpenseCategoriesUseCase,
    private val createExpenseUseCase: CreateExpenseUseCase,

):ViewModel(){


    val expenseCategories = getAllExpenseCategoriesUseCase()

    val _expenseName = mutableStateOf("")
    val expenseName: State<String> = _expenseName

    val _expenseAmount = mutableStateOf(0)
    val expenseAmount: State<Int> = _expenseAmount

    val _selectedIndex = mutableStateOf(0)
    val selectedIndex: State<Int> = _selectedIndex

    val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> =_isLoading

    val _selectedExpenseCategory = mutableStateOf<ExpenseCategory?>(null)
    val selectedExpenseCategory: State<ExpenseCategory?> = _selectedExpenseCategory



    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onChangeExpenseName(text: String) {
        _expenseName.value = text

    }

    fun onChangeSelectedIndex(index: Int) {
        _selectedIndex.value = index

    }

    fun onChangeExpenseAmount(text: String) {
        if (text.isBlank()){
            _expenseAmount.value = 0
            return
        }
        if (isNumeric(text)){
            _expenseAmount.value = text.toInt()

        }else{
            _expenseAmount.value = 0
        }


    }

    fun onChangeSelectedExpenseCategory(category:ExpenseCategory) {
        _selectedExpenseCategory.value = category

    }


    fun addExpense(){
        viewModelScope.launch {
            if (_expenseAmount.value == 0 || _expenseName.value ==""){
                _eventFlow.emit(UiEvent.ShowSnackbar(
                    uiText = "Please enter a valid expense"
                ))
                return@launch
            }
            if (_selectedExpenseCategory.value == null){
                _eventFlow.emit(UiEvent.ShowSnackbar(
                    uiText = "Please select an expense category"
                ))
                return@launch
            }
            val newExpense = Expense(
                expenseId = UUID.randomUUID().toString(),
                expenseName = expenseName.value,
                expenseAmount = expenseAmount.value,
                expenseCategoryId = _selectedExpenseCategory.value!!.expenseCategoryId,
                expenseCreatedAt = SimpleDateFormat("hh:mm:ss").format(Date()),
                expenseUpdatedAt = SimpleDateFormat("hh:mm:ss").format(Date()),
                expenseCreatedOn = SimpleDateFormat("dd/mm/yyyy").format(Date()),
                expenseUpdatedOn = SimpleDateFormat("dd/mm/yyyy").format(Date())

            )
            createExpenseUseCase(expense = newExpense).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _isLoading.value = true

                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        _eventFlow.emit(UiEvent.ShowSnackbar(
                            uiText = result.data?.msg ?: "Expense  added successfully"))
                        _expenseName.value = ""
                        _expenseAmount.value = 0
                        _selectedExpenseCategory.value = null
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
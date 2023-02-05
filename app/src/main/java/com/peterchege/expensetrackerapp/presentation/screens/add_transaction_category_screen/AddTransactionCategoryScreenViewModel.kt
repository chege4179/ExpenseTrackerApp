package com.peterchege.expensetrackerapp.presentation.screens.add_transaction_category_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.core.util.Resource
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import com.peterchege.expensetrackerapp.domain.use_case.CreateTransactionCategoryUseCase
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
class AddTransactionCategoryScreenViewModel @Inject constructor(
    private val createTransactionCategoryUseCase: CreateTransactionCategoryUseCase,


) : ViewModel(){
    val _transactionCategoryName = mutableStateOf("")
    val transactionCategoryName: State<String> = _transactionCategoryName


    val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> =_isLoading



    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeTransactionCategoryName(text: String) {
        _transactionCategoryName.value = text

    }


    fun addTransactionCategory() {
        viewModelScope.launch {
            if (_transactionCategoryName.value.isBlank()) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Expense Category Name cannot be black"))
            } else {
                val transactionCategory = TransactionCategory(
                    transactionCategoryName = _transactionCategoryName.value,
                    transactionCategoryId = UUID.randomUUID().toString(),
                    transactionCategoryCreatedAt = Date(),
                )
                createTransactionCategoryUseCase(transactionCategory = transactionCategory).onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _isLoading.value = true

                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                uiText = result.data?.msg ?: "Transaction Category added successfully"))
                            _transactionCategoryName.value = ""
                        }
                        is Resource.Error ->{
                            _isLoading.value = false
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                uiText = result.message ?:"An error occurred"))

                        }
                    }
                }.launchIn(this)


            }
        }

    }



}
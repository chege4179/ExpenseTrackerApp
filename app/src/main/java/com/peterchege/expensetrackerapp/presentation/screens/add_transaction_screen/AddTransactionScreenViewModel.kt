package com.peterchege.expensetrackerapp.presentation.screens.add_transaction_screen

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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTransactionScreenViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getAllTransactionCategoriesUseCase: GetAllTransactionCategoriesUseCase,
    ) : ViewModel() {

    val transactionCategories = getAllTransactionCategoriesUseCase()


    val _transactionName = mutableStateOf("")
    val transactionName: State<String> = _transactionName

    val _transactionAmount = mutableStateOf(0)
    val transactionAmount: State<Int> = _transactionAmount

    val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    val _selectedTransactionCategory = mutableStateOf<TransactionCategory?>(null)
    val selectedTransactionCategory: State<TransactionCategory?> = _selectedTransactionCategory

    val _selectedIndex = mutableStateOf(0)
    val selectedIndex: State<Int> = _selectedIndex

    val _transactionTime = mutableStateOf<LocalTime>(LocalTime.now())
    val transactionTime: State<LocalTime> = _transactionTime

    val _transactionDate = mutableStateOf<LocalDate>(LocalDate.now())
    val transactionDate: State<LocalDate> = _transactionDate


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeTransactionDate(date: LocalDate) {
        _transactionDate.value = date
    }

    fun onChangeTransactionTime(time: LocalTime) {
        _transactionTime.value = time
    }

    fun onChangeTransactionName(text: String) {
        _transactionName.value = text

    }

    fun onChangeSelectedIndex(index: Int) {
        _selectedIndex.value = index

    }

    fun onChangeSelectedTransactionCategory(category: TransactionCategory) {
        _selectedTransactionCategory.value = category

    }

    fun onChangeTransactionAmount(text: String) {
        if (text.isBlank()) {
            _transactionAmount.value = 0
            return
        }
        if (isNumeric(text)) {
            _transactionAmount.value = text.toInt()

        } else {
            _transactionAmount.value = 0
        }
    }


    fun addTransaction() {
        viewModelScope.launch {
            if (_transactionName.value.isBlank()) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Transaction Name cannot be black"))
                return@launch
            }
            if (_selectedTransactionCategory.value == null) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Please select a transaction Category"))
                return@launch
            }
            val date = localDateTimeToDate(
                localDate = _transactionDate.value,
                localTime = _transactionTime.value
            )

            val transaction = Transaction(
                transactionName = _transactionName.value,
                transactionAmount = _transactionAmount.value,
                transactionCategoryId = _selectedTransactionCategory.value!!.transactionCategoryId,
                transactionId = UUID.randomUUID().toString(),
                transactionCreatedAt = SimpleDateFormat("hh:mm:ss").format(date) ,
                transactionUpdatedAt = SimpleDateFormat("hh:mm:ss").format(date),
                transactionUpdatedOn = generateFormatDate(date = _transactionDate.value),
                transactionCreatedOn = generateFormatDate(date = _transactionDate.value),



            )
            createTransactionUseCase(transaction = transaction).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _isLoading.value = true

                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = result.data?.msg ?: "Transaction added successfully"
                            )
                        )
                        _transactionAmount.value = 0
                        _transactionName.value = ""
                    }
                    is Resource.Error -> {
                        _isLoading.value = false
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
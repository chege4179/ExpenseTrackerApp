package com.peterchege.expensetrackerapp.presentation.screens.transaction_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.domain.use_case.GetSingleTransactionUseCase
import com.peterchege.expensetrackerapp.domain.use_case.TransactionInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSingleTransactionUseCase: GetSingleTransactionUseCase,

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

}
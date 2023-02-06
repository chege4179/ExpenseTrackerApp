package com.peterchege.expensetrackerapp.presentation.screens.home_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.core.util.Resource
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.domain.use_case.GetFilteredTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getFilteredTransactionsUseCase: GetFilteredTransactionsUseCase
) : ViewModel() {

    val _selectedIndex = mutableStateOf(0)
    val selectedIndex: State<Int> = _selectedIndex

    val _transactions = mutableStateOf<List<Transaction>>(emptyList())
    val transactions: State<List<Transaction>> = _transactions


    init {
        getTransactions(filter = FilterConstants.FilterList[_selectedIndex.value])
    }


    fun onChangeSelectedIndex(index: Int) {
        _selectedIndex.value = index
        getTransactions(filter = FilterConstants.FilterList[index])
    }


    fun getTransactions(filter: String) {
        viewModelScope.launch {
            getFilteredTransactionsUseCase(filter = filter).collect {
                _transactions.value = it.map { it.toExternalModel() }
            }
        }


    }


}
package com.peterchege.expensetrackerapp.presentation.screens.home_screen

import androidx.lifecycle.ViewModel
import com.peterchege.expensetrackerapp.domain.use_case.GetAllTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
) : ViewModel() {

    val transactions = getAllTransactionsUseCase()



}
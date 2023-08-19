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
package com.peterchege.expensetrackerapp.presentation.screens.all_income_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.domain.models.Income
import com.peterchege.expensetrackerapp.domain.use_case.GetAllIncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface AllIncomeScreenUiState {
    object Loading:AllIncomeScreenUiState

    data class Success(val incomes:List<Income>):AllIncomeScreenUiState

    data class Error(val message:String):AllIncomeScreenUiState
}


@HiltViewModel
class AllIncomeScreenViewModel @Inject constructor(
    private val getAllIncomeUseCase: GetAllIncomeUseCase,
): ViewModel() {

    val uiState = getAllIncomeUseCase()
        .map {
            AllIncomeScreenUiState.Success(it)
        }
        .onStart { AllIncomeScreenUiState.Loading }
        .catch { AllIncomeScreenUiState.Error(message = "Failed to your data") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = AllIncomeScreenUiState.Loading
        )


    private val _activeIncomeId = mutableStateOf<String?>(null)
    val activeIncomeId : State<String?> = _activeIncomeId


    fun onChangeActiveIncomeId(id:String) {
        _activeIncomeId.value = id
    }

}
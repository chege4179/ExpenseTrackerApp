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
package com.peterchege.expensetrackerapp.presentation.screens.analytics.expenses

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategoryInfo
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategoryInfoEntity
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.domain.use_case.GetAllExpenseCategoriesUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetExpensesByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ExpensesAnalyticsScreenUiState {
    object Loading:ExpensesAnalyticsScreenUiState
    data class Success(val expenseCategories :List<ExpenseCategoryInfo>):ExpensesAnalyticsScreenUiState

    data class Error(val message:String):ExpensesAnalyticsScreenUiState
}

@HiltViewModel
class ExpenseAnalyticsScreenViewModel @Inject constructor(
    private val getAllExpenseCategoriesUseCase: GetAllExpenseCategoriesUseCase,
    private val getExpensesByCategoryUseCase: GetExpensesByCategoryUseCase,

    ) : ViewModel() {

    val _expenseCategories = mutableStateOf<List<ExpenseCategoryInfoEntity>>(emptyList())
    val expenseCategories: State<List<ExpenseCategoryInfoEntity>> = _expenseCategories

    init {
        viewModelScope.launch {
            getAllExpenseCategoriesUseCase().collect { expenseCategoryEntities ->
                val expenseCategories =
                    expenseCategoryEntities.map { entity -> entity.toExternalModel() }
                val expenseCategoriesInfo = expenseCategories.map {
                    val expenses = getExpensesByCategoryUseCase(it.expenseCategoryId)
                    ExpenseCategoryInfoEntity(
                        expenseCategory = it,
                        expenses = expenses
                    )
                }
                _expenseCategories.value = expenseCategoriesInfo

            }
        }

    }


}
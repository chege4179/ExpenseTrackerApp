/*
 * Copyright 2026 Expense Tracker App By Peter Chege
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
package com.peterchege.expensetrackerapp.presentation.screens.expenses

import com.peterchege.expensetrackerapp.presentation.preview.ThemePreviews
import com.peterchege.expensetrackerapp.presentation.preview.PreviewSurface
import com.peterchege.expensetrackerapp.presentation.preview.PreviewData
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.presentation.components.ErrorComponent
import com.peterchege.expensetrackerapp.presentation.components.ExpenseCard
import com.peterchege.expensetrackerapp.presentation.components.ExpenseFilterCard
import com.peterchege.expensetrackerapp.presentation.components.LoadingComponent

@Composable
fun AllExpensesScreen(
    navigateToExpenseScreen: (String) -> Unit,
    viewModel: AllExpensesScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val activeExpenseFilter by viewModel.activeExpenseFilter.collectAsStateWithLifecycle()

    AllExpensesScreenContent(
        activeExpenseFilter = activeExpenseFilter,
        uiState = uiState,
        onChangeActiveExpenseFilter = {
            viewModel.onChangeActiveExpenseFilter(it)
        },
        navigateToExpenseScreen = navigateToExpenseScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AllExpensesScreenContent(
    activeExpenseFilter: String,
    uiState: AllExpensesScreenUiState,
    onChangeActiveExpenseFilter: (String) -> Unit,
    navigateToExpenseScreen: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                        text = "My Expenses",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    scrolledContainerColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.background,
                )
            )
        },
    ) { paddingValues ->
        when (uiState) {
            is AllExpensesScreenUiState.Loading -> {
                LoadingComponent()
            }

            is AllExpensesScreenUiState.Error -> {
                ErrorComponent(message = uiState.message)
            }

            is AllExpensesScreenUiState.Success -> {
                val expenseCategories = uiState.expenseCategories
                val expenses = uiState.expenses
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(5.dp)
                ) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            ExpenseFilterCard(
                                filterName = FilterConstants.ALL,
                                isActive = activeExpenseFilter == FilterConstants.ALL,
                                onClick = {
                                    onChangeActiveExpenseFilter(FilterConstants.ALL)
                                }
                            )
                        }
                        items(
                            items = expenseCategories,
                            key = { it.expenseCategoryId }) { category ->
                            ExpenseFilterCard(
                                filterName = category.expenseCategoryName,
                                isActive = activeExpenseFilter == category.expenseCategoryId,
                                onClick = {
                                    onChangeActiveExpenseFilter(category.expenseCategoryId)

                                }
                            )
                        }
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(items = expenses, key = { it.expenseId }) { expense ->
                            val isVisible = if (activeExpenseFilter == FilterConstants.ALL) true else (activeExpenseFilter == expense.expenseCategoryId)
                            AnimatedVisibility(
                                visible = isVisible
                            ) {
                                ExpenseCard(
                                    expense = expense,
                                    onExpenseNavigate = {
                                        navigateToExpenseScreen(it)
                                    }
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@ThemePreviews
@Composable
private fun AllExpensesScreenPreview() {
    PreviewSurface {
        AllExpensesScreenContent(
            activeExpenseFilter = FilterConstants.ALL,
            uiState = AllExpensesScreenUiState.Success(
                expenses = PreviewData.expenses,
                expenseCategories = PreviewData.expenseCategories,
            ),
            onChangeActiveExpenseFilter = {},
            navigateToExpenseScreen = {},
        )
    }
}

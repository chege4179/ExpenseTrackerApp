package com.peterchege.expensetrackerapp.presentation.screens.expenses

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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.annotation.ExperimentalCoilApi
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

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
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
                colors = TopAppBarColors(
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
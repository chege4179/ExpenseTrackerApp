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
package com.peterchege.expensetrackerapp.presentation.screens.expense

import com.peterchege.expensetrackerapp.presentation.components.DeleteButton
import com.peterchege.expensetrackerapp.presentation.components.DetailsCard
import com.peterchege.expensetrackerapp.presentation.preview.ThemePreviews
import com.peterchege.expensetrackerapp.presentation.preview.PreviewSurface
import com.peterchege.expensetrackerapp.presentation.preview.PreviewData
import com.peterchege.expensetrackerapp.domain.use_case.ExpenseInfo
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.expensetrackerapp.R
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.presentation.alertDialogs.ConfirmDeleteExpenseDialog
import com.peterchege.expensetrackerapp.presentation.components.ErrorComponent
import com.peterchege.expensetrackerapp.presentation.components.LoadingComponent

@Composable
fun ExpenseScreen(
    viewModel: ExpenseScreenViewModel = hiltViewModel(),
    navigateBack:() -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val deleteExpenseUiState by viewModel.deleteExpenseUiState.collectAsStateWithLifecycle()
    ExpenseScreenContent(
        uiState = uiState,
        deleteExpenseUiState = deleteExpenseUiState,
        setDeleteExpense = viewModel::setDeleteExpense,
        toggleDeleteExpenseDialog = viewModel::toggleDeleteExpenseDialogVisibility,
        deleteExpense = { viewModel.deleteExpense(navigateBack) }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ExpenseScreenContent(
    uiState: ExpenseScreenUiState,
    deleteExpenseUiState: DeleteExpenseUiState,
    setDeleteExpense: (Expense?) -> Unit,
    toggleDeleteExpenseDialog: () -> Unit,
    deleteExpense: () -> Unit,
) {

    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                        text = stringResource(id = R.string.expense),
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
            is ExpenseScreenUiState.Loading -> {
                LoadingComponent()
            }

            is ExpenseScreenUiState.Error -> {
                ErrorComponent(message = uiState.message)
            }

            is ExpenseScreenUiState.Success -> {
                val transactionInfo = uiState.expenseInfo
                if (deleteExpenseUiState.isDeleteExpenseDialogVisible &&
                    deleteExpenseUiState.deleteExpense != null
                ) {
                    ConfirmDeleteExpenseDialog(
                        closeDeleteDialog = toggleDeleteExpenseDialog,
                        expense = deleteExpenseUiState.deleteExpense,
                        deleteExpense = deleteExpense
                    )
                }
                transactionInfo.let {
                    if (it.expense != null){
                        val expense = it.expense
                        val category = it.expenseCategory
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.background)
                                .padding(paddingValues)
                                .padding(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                DetailsCard(
                                    title = expense.expenseName,
                                    amount = expense.expenseAmount,
                                    details = listOf(
                                        "Category" to category.expenseCategoryName,
                                        "Date" to expense.expenseCreatedOn,
                                        "Time" to expense.expenseCreatedAt,
                                        "Expense ID" to expense.expenseId,
                                    ),
                                )

                            }
                            DeleteButton(
                                text = stringResource(id = R.string.delete_expense),
                                onClick = {
                                    setDeleteExpense(it.expense)
                                },
                            )
                        }
                    }
                }
            }
        }

    }
}

@ThemePreviews
@Composable
private fun ExpenseScreenPreview() {
    PreviewSurface {
        ExpenseScreenContent(
            uiState = ExpenseScreenUiState.Success(
                expenseInfo = ExpenseInfo(
                    expense = PreviewData.expense,
                    expenseCategory = PreviewData.expenseCategories.first(),
                )
            ),
            deleteExpenseUiState = DeleteExpenseUiState(isDeleteExpenseDialogVisible = false),
            setDeleteExpense = {},
            toggleDeleteExpenseDialog = {},
            deleteExpense = {},
        )
    }
}

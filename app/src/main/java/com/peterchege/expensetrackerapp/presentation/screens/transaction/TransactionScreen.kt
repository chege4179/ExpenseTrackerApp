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
package com.peterchege.expensetrackerapp.presentation.screens.transaction

import com.peterchege.expensetrackerapp.presentation.components.DeleteButton
import com.peterchege.expensetrackerapp.presentation.components.DetailsCard
import com.peterchege.expensetrackerapp.presentation.preview.ThemePreviews
import com.peterchege.expensetrackerapp.presentation.preview.PreviewSurface
import com.peterchege.expensetrackerapp.presentation.preview.PreviewData
import com.peterchege.expensetrackerapp.domain.models.TransactionInfo
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
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.presentation.alertDialogs.ConfirmDeleteTransactionDialog
import com.peterchege.expensetrackerapp.presentation.components.ErrorComponent
import com.peterchege.expensetrackerapp.presentation.components.LoadingComponent

@Composable
fun TransactionScreen(
    viewModel: TransactionScreenViewModel = hiltViewModel(),
    navigateBack:() -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val deleteTransactionUiState by viewModel.deleteTransactionUiState.collectAsStateWithLifecycle()


    TransactionScreenContent(
        deleteTransactionUiState = deleteTransactionUiState,
        uiState = uiState,
        deleteTransaction = { viewModel.deleteTransaction(navigateBack) },
        setDeleteTransaction = viewModel::setDeleteTransaction,
        toggleDeleteTransactionVisibility = viewModel::toggleDeleteDialogVisibility

    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreenContent(
    deleteTransactionUiState: DeleteTransactionUiState,
    uiState: TransactionScreenUiState,
    deleteTransaction: () -> Unit,
    setDeleteTransaction: (Transaction?) -> Unit,
    toggleDeleteTransactionVisibility: () -> Unit,

    ) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                        text = stringResource(id = R.string.transactions),
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
            is TransactionScreenUiState.Loading -> {
                LoadingComponent()
            }

            is TransactionScreenUiState.Error -> {
                ErrorComponent(message = uiState.message)
            }

            is TransactionScreenUiState.Success -> {
                val transactionInfo = uiState.transactionInfo
                if (deleteTransactionUiState.isDeleteTransactionDialogVisible &&
                    deleteTransactionUiState.deleteTransaction != null) {
                    ConfirmDeleteTransactionDialog(
                        closeDeleteDialog = toggleDeleteTransactionVisibility,
                        transaction = deleteTransactionUiState.deleteTransaction,
                        deleteTransaction = deleteTransaction
                    )
                }
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
                        transactionInfo.transaction?.let { transaction ->
                            DetailsCard(
                                title = transaction.transactionName,
                                amount = transaction.transactionAmount,
                                details = listOfNotNull(
                                    transactionInfo.category?.let { "Category" to it.transactionCategoryName },
                                    "Date" to transaction.transactionCreatedOn,
                                    "Time" to transaction.transactionCreatedAt,
                                    "Transaction ID" to transaction.transactionId,
                                ),
                            )
                        }
                    }
                    DeleteButton(
                        text = stringResource(id = R.string.delete_transaction),
                        onClick = {
                            setDeleteTransaction(transactionInfo.transaction)
                            toggleDeleteTransactionVisibility()
                        },
                    )
                }
            }
        }

    }
}

@ThemePreviews
@Composable
private fun TransactionScreenPreview() {
    PreviewSurface {
        TransactionScreenContent(
            deleteTransactionUiState = DeleteTransactionUiState(),
            uiState = TransactionScreenUiState.Success(
                transactionInfo = TransactionInfo(
                    transaction = PreviewData.transaction,
                    category = PreviewData.transactionCategories.first(),
                )
            ),
            deleteTransaction = {},
            setDeleteTransaction = {},
            toggleDeleteTransactionVisibility = {},
        )
    }
}

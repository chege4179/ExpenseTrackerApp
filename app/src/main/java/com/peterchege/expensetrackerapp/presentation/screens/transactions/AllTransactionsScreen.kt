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
package com.peterchege.expensetrackerapp.presentation.screens.transactions

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.presentation.components.ErrorComponent
import com.peterchege.expensetrackerapp.presentation.components.LoadingComponent
import com.peterchege.expensetrackerapp.presentation.components.TransactionCard
import com.peterchege.expensetrackerapp.presentation.components.TransactionFilterCard
import com.peterchege.expensetrackerapp.R

@Composable
fun AllTransactionsScreen(
    viewModel: AllTransactionsScreenViewModel = hiltViewModel(),
    navigateToTransactionScreen:(String) -> Unit,
) {
    val activeTransactionFilter by viewModel.activeTransactionFilter.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AllTransactionsScreenContent(
        activeTransactionFilter = activeTransactionFilter,
        uiState = uiState,
        onChangeActiveTransactionFilter = { viewModel.onChangeActiveTransactionFilter(it) },
        navigateToTransactionScreen = navigateToTransactionScreen
    )
}


@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AllTransactionsScreenContent(
    activeTransactionFilter: String,
    uiState: AllTransactionsScreenUiState,
    onChangeActiveTransactionFilter: (String) -> Unit,
    navigateToTransactionScreen:(String) -> Unit,
    ) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                        text = stringResource(id = R.string.my_transactions),
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
        when(uiState){
            is AllTransactionsScreenUiState.Loading -> {
                LoadingComponent()
            }
            is AllTransactionsScreenUiState.Error -> {
                ErrorComponent(message = uiState.message)
            }
            is AllTransactionsScreenUiState.Success -> {
                val transactionCategories = uiState.transactionCategories
                val transactions = uiState.transactions
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
                            TransactionFilterCard(
                                filterName = FilterConstants.ALL,
                                isActive = activeTransactionFilter == FilterConstants.ALL,
                                onClick = {
                                    onChangeActiveTransactionFilter(FilterConstants.ALL)
                                }
                            )
                        }
                        items(items = transactionCategories) { category ->
                            TransactionFilterCard(
                                filterName = category.transactionCategoryName,
                                isActive = activeTransactionFilter == category.transactionCategoryId,
                                onClick = {
                                    onChangeActiveTransactionFilter(category.transactionCategoryId)

                                }
                            )
                        }
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {

                        items(items = transactions) { transaction ->
                            TransactionCard(
                                transaction = transaction,
                                onTransactionNavigate = navigateToTransactionScreen
                            )
                        }
                    }
                }
            }
        }


    }

}
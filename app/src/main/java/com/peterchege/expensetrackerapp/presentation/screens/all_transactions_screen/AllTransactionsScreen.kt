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
package com.peterchege.expensetrackerapp.presentation.screens.all_transactions_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.components.TransactionCard
import com.peterchege.expensetrackerapp.presentation.components.TransactionFilterCard

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AllTransactionsScreen(
    viewModel: AllTransactionsScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val activeTransactionFilter = viewModel._activeTransactionCategoryFilterId.value
    val transactionCategories = viewModel.transactionCategories.collectAsStateWithLifecycle()
    val transactions = viewModel.transactions.value

    LaunchedEffect(key1 = activeTransactionFilter){
        viewModel.getTransactions()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.onBackground,
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        text = "My Transactions",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                            viewModel.onChangeActiveTransactionFilter(
                                filter = FilterConstants.ALL)
                        }
                    )
                }
                items(items = transactionCategories.value) { category ->
                    TransactionFilterCard(
                        filterName = category.transactionCategoryName,
                        isActive = activeTransactionFilter == category.transactionCategoryId,
                        onClick = {
                            viewModel.onChangeActiveTransactionFilter(
                                filter = category.transactionCategoryId)

                        }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ){

                items(items = transactions) { transaction ->
                    TransactionCard(
                        transaction = transaction,
                        onTransactionNavigate = {
                            navController.navigate(Screens.TRANSACTIONS_SCREEN + "/$it")

                        }
                    )
                }
            }
        }

    }

}
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
package com.peterchege.expensetrackerapp.presentation.screens.search_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.domain.models.TransactionCategory
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.components.MenuSample
import com.peterchege.expensetrackerapp.presentation.components.TransactionCard
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val transactionCategories = viewModel.transactionCategories
        .collectAsStateWithLifecycle(initialValue = emptyList())
        .value
        .map { it.toExternalModel() }


    SearchScreenContent(
        eventFlow = viewModel.eventFlow,
        navController = navController,
        uiState = uiState.value,
        transactionCategories = transactionCategories,
        searchTransactions = { viewModel.searchTransactions() },
        onChangeTransactionCategory = { viewModel.onChangeTransactionCategory(it) },
        onChangeTransactionStartDate = { viewModel.onChangeTransactionStartDate(it) },
        onChangeTransactionEndDate = { viewModel.onChangeTransactionEndDate(it) }
    )


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreenContent(
    eventFlow: SharedFlow<UiEvent>,
    navController: NavController,
    uiState: SearchScreenState,
    transactionCategories: List<TransactionCategory>,
    searchTransactions: () -> Unit,
    onChangeTransactionCategory: (TransactionCategory) -> Unit,
    onChangeTransactionStartDate: (LocalDate) -> Unit,
    onChangeTransactionEndDate: (LocalDate) -> Unit


) {
    val scaffoldState = rememberScaffoldState()

    val startDateDialogState = rememberMaterialDialogState()
    val endDateDialogState = rememberMaterialDialogState()



    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText
                    )
                }

                is UiEvent.Navigate -> {
                    navController.navigate(route = event.route)
                }

                else -> {}
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.onBackground,
                title = {
                    Text(
                        text = "Search",
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
                .padding(10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(3.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(
                            text = uiState.startDate.toString(),

                            style = TextStyle(color = MaterialTheme.colors.primary)
                        )
                        Button(
                            modifier = Modifier
                                .width(width = 100.dp),
                            onClick = { startDateDialogState.show() },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.onBackground
                            )
                        ) {
                            Text(
                                text = "Start date",
                                style = TextStyle(color = MaterialTheme.colors.primary)
                            )
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(3.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(
                            text = uiState.endDate.toString(),
                            style = TextStyle(color = MaterialTheme.colors.primary)
                        )
                        Button(
                            modifier = Modifier
                                .width(width = 100.dp),
                            onClick = { endDateDialogState.show() },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.onBackground
                            )
                        ) {
                            Text(
                                text = "End date",
                                style = TextStyle(color = MaterialTheme.colors.primary)
                            )
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(end = 5.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(
                            text = "Category",
                            modifier = Modifier.fillMaxWidth(0.5f),
                            style = TextStyle(color = MaterialTheme.colors.primary)
                        )
                        val currentIndex =
                            if (uiState.transactionCategory == null) 0
                            else
                                transactionCategories
                                    .map { it.transactionCategoryName }
                                    .indexOf(uiState.transactionCategory.transactionCategoryName)
                        MenuSample(
                            menuWidth = 120,
                            selectedIndex = currentIndex,
                            menuItems = transactionCategories.map { transactionCategory -> transactionCategory.transactionCategoryName },
                            onChangeSelectedIndex = {

                                val selectedTransactionCategory = transactionCategories[it]
                                onChangeTransactionCategory(selectedTransactionCategory)

                            }
                        )

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(end = 5.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        IconButton(
                            onClick = {
                                searchTransactions()

                            },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary
                            )

                        }

                    }

                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (uiState.transactions.isEmpty()) {
                        Text(
                            text = "No transactions found ",
                            style = TextStyle(color = MaterialTheme.colors.primary),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (uiState.transactions.isNotEmpty()) {
                        Text(
                            text = "A total of ${uiState.transactions.size} transactions have been made " +
                                    "between ${uiState.startDate}" +
                                    " and ${uiState.endDate}",
                            style = TextStyle(color = MaterialTheme.colors.primary),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                }

            }
            items(items = uiState.transactions) { transaction ->
                TransactionCard(
                    transaction = transaction,
                    onTransactionNavigate = {
                        navController.navigate(Screens.TRANSACTIONS_SCREEN + "/$it")

                    }
                )
            }


        }
        MaterialDialog(
            dialogState = startDateDialogState,
            buttons = {
                positiveButton(text = "Pick") {
                    startDateDialogState.hide()

                }
                negativeButton(text = "Cancel") {

                }
            }
        ) {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a start date",
            ) {
                onChangeTransactionStartDate(it)

            }

        }
        MaterialDialog(
            dialogState = endDateDialogState,
            buttons = {
                positiveButton(text = "Pick") {
                    endDateDialogState.hide()

                }
                negativeButton(text = "Cancel") {

                }
            }
        ) {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick an End date",
            ) {
                onChangeTransactionEndDate(it)

            }

        }

    }

}
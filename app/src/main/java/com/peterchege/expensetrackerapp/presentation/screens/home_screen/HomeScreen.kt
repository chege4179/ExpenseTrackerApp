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
package com.peterchege.expensetrackerapp.presentation.screens.home_screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.core.util.TestTags
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.models.Income
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.bottomsheets.view.AddExpenseBottomSheet
import com.peterchege.expensetrackerapp.presentation.bottomsheets.view.AddExpenseCategoryBottomSheet
import com.peterchege.expensetrackerapp.presentation.bottomsheets.view.AddIncomeBottomSheet
import com.peterchege.expensetrackerapp.presentation.bottomsheets.view.AddTransactionBottomSheet
import com.peterchege.expensetrackerapp.presentation.bottomsheets.view.AddTransactionCategoryBottomSheet
import com.peterchege.expensetrackerapp.presentation.components.HomeScreenActionsCard
import com.peterchege.expensetrackerapp.presentation.components.IncomeCard
import com.peterchege.expensetrackerapp.presentation.components.MenuSample
import com.peterchege.expensetrackerapp.presentation.components.TransactionCard
import com.peterchege.expensetrackerapp.presentation.theme.GreyColor
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

enum class MultiFloatingState {
    EXPANDED,
    COLLAPSED
}

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
){
    LaunchedEffect(key1 = viewModel.selectedIndex.value) {
        viewModel.getTransactions(filter = FilterConstants.FilterList[viewModel.selectedIndex.value])
    }
    val transactions = viewModel.transactions
        .value
        .collectAsStateWithLifecycle(initialValue = emptyList())
        .value
        .map { it.toExternalModel() }

    val expenses = viewModel.expenses
        .collectAsStateWithLifecycle()
        .value
        .map { it.toExternalModel() }

    val income = viewModel.income.collectAsStateWithLifecycle()


    HomeScreenContent(
        eventFlow = viewModel.eventFlow,
        navController = navController,
        activeBottomSheet = viewModel.activeBottomSheet.value,
        transactions = transactions,
        onChangeActiveBottomSheet = { viewModel.onChangeActiveBottomSheet(it) },
        incomes = income.value,
        expenses = expenses
    )

}

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    eventFlow: SharedFlow<UiEvent>,
    navController:NavController,
    activeBottomSheet:BottomSheets?,
    transactions:List<Transaction>,
    onChangeActiveBottomSheet:(BottomSheets) -> Unit,
    incomes:List<Income>,
    expenses:List<Expense>,
    ) {
    val totalIncome = incomes.sumOf { it.incomeAmount }
    val totalExpense = expenses.sumOf { it.expenseAmount }
    val totalTransaction = transactions.sumOf { it.transactionAmount }

    val remainingIncome = totalIncome - (totalExpense + totalTransaction)

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
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
                is UiEvent.OpenBottomSheet -> {
                    modalSheetState.show()
                }
            }
        }
    }



    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetBackgroundColor = MaterialTheme.colors.onBackground,

        sheetContent = {
            when(activeBottomSheet){
                BottomSheets.ADD_EXPENSE -> {
                    AddExpenseBottomSheet(navController = navController)
                }
                BottomSheets.ADD_EXPENSE_CATEGORY -> {
                    AddExpenseCategoryBottomSheet(navController = navController)
                }
                BottomSheets.ADD_TRANSACTION -> {
                    AddTransactionBottomSheet(navController = navController)
                }
                BottomSheets.ADD_TRANSACTION_CATEGORY -> {
                    AddTransactionCategoryBottomSheet(navController = navController)
                }
                BottomSheets.ADD_INCOME -> {
                    AddIncomeBottomSheet(navController = navController)
                }
                else -> {}
            }

        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag(tag = Screens.HOME_SCREEN),
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.onBackground,
                    title = {
                        Text(
                            style = TextStyle(color = MaterialTheme.colors.primary),
                            text = "My Expense Tracker App",
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
                    .padding(10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(100.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Your Income ",
                                style = TextStyle(color = GreyColor),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "KES $remainingIncome /=",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                style = TextStyle(color = MaterialTheme.colors.primary)
                            )

                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            HomeScreenActionsCard(
                                name ="Add Income",
                                icon = Icons.Outlined.Payments,
                                onClick = {
                                    onChangeActiveBottomSheet(
                                        BottomSheets.ADD_INCOME)
                                }
                            )
                            HomeScreenActionsCard(
                                name ="Add Transaction",
                                icon = Icons.Outlined.ReceiptLong,
                                onClick = {
                                    onChangeActiveBottomSheet(
                                        BottomSheets.ADD_TRANSACTION)
                                }
                            )
                            HomeScreenActionsCard(
                                name ="Add Expense",
                                icon = Icons.Outlined.ShoppingCart,
                                onClick = {
                                    onChangeActiveBottomSheet(
                                        BottomSheets.ADD_EXPENSE)
                                }
                            )
                            HomeScreenActionsCard(
                                name ="Add Transaction Category",
                                icon = Icons.Default.Add,
                                onClick = {
                                    onChangeActiveBottomSheet(
                                        BottomSheets.ADD_TRANSACTION_CATEGORY)
                                }
                            )
                            HomeScreenActionsCard(
                                name ="Add Expense Category",
                                icon = Icons.Default.Add,
                                onClick = {
                                    onChangeActiveBottomSheet(
                                        BottomSheets.ADD_EXPENSE_CATEGORY)
                                }
                            )

                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .padding(end = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Income",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(
                                    color = MaterialTheme.colors.primary,
                                )
                            )
                            IconButton(onClick = {
                                navController.navigate(route = Screens.ALL_INCOME_SCREEN)
                            }) {
                                Text(
                                    text = "See All",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    style = TextStyle(
                                        color = MaterialTheme.colors.primary,
                                    )
                                )
                            }

                        }
                    }
                    items(items = incomes.take( n= 2)) { income ->
                        IncomeCard(
                            income = income,
                            onIncomeNavigate = {

                            }
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .padding(end = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Transactions",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(
                                    color = MaterialTheme.colors.primary,
                                )
                            )
                            IconButton(onClick = {
                                navController.navigate(Screens.ALL_TRANSACTIONS_SCREEN)
                            }) {
                                Text(
                                    text = "See All",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    style = TextStyle(
                                        color = MaterialTheme.colors.primary,
                                    )
                                )
                            }

                        }
                    }
                    items(items = transactions.take(n= 2)) { transaction ->
                        TransactionCard(
                            transaction = transaction,
                            onTransactionNavigate = {
                                navController.navigate(route = Screens.TRANSACTIONS_SCREEN + "/$it")

                            }
                        )
                    }
                    item {

                    }
                }
            }
        }
    }
}




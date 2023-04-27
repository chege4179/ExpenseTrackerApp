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
package com.peterchege.expensetrackerapp.presentation.bottomsheets.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.core.util.getNumericInitialValue
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.AddExpenseFormState
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.AddExpenseScreenViewModel
import com.peterchege.expensetrackerapp.presentation.components.MenuSample
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddExpenseBottomSheet(
    navController: NavController,
    viewModel: AddExpenseScreenViewModel = hiltViewModel()
) {
    val expenseCategories = viewModel.expenseCategories
        .collectAsStateWithLifecycle(initialValue = emptyList())
        .value
        .map { it.toExternalModel() }

    val formState = viewModel.formState.collectAsStateWithLifecycle()

    AddExpenseBottomSheetContent(
        eventFlow = viewModel.eventFlow,
        expenseCategories = expenseCategories,
        navController = navController,
        formState = formState.value,
        onChangeExpenseName = {
            viewModel.onChangeExpenseName(it)
        },
        onChangeExpenseAmount = {
            viewModel.onChangeExpenseAmount(it)
        },
        onChangeExpenseCategory = {
            viewModel.onChangeSelectedExpenseCategory(it)
        },
        addExpense = {
            viewModel.addExpense()
        }
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddExpenseBottomSheetContent(
    eventFlow: SharedFlow<UiEvent>,
    expenseCategories: List<ExpenseCategory>,
    navController: NavController,
    formState: AddExpenseFormState,
    onChangeExpenseName: (String) -> Unit,
    onChangeExpenseAmount: (String) -> Unit,
    onChangeExpenseCategory: (ExpenseCategory) -> Unit,
    addExpense: () -> Unit,

    ) {
    val scaffoldState = rememberScaffoldState()
    val keyBoard = LocalSoftwareKeyboardController.current


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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(370.dp)
    ) {
        if (formState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .fillMaxSize()
                .padding(10.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(color = MaterialTheme.colors.primary),
                    text = "Create Expense ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
            TextField(
                value = formState.expenseName,
                onValueChange = {
                    onChangeExpenseName(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Expense Name"
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.primary
                )

            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,

                ) {
                TextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    value = getNumericInitialValue(formState.expenseAmount),
                    onValueChange = {
                        onChangeExpenseAmount(it)
                    },

                    modifier = Modifier.fillMaxWidth(0.5f),
                    placeholder = {
                        Text(
                            text = "Expense Amount",
                            style = TextStyle(
                                color = MaterialTheme.colors.primary
                            )
                        )
                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.primary
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                val currentIndex =
                    if (formState.selectedExpenseCategory == null) 0
                else
                    expenseCategories.map { it.expenseCategoryName }.indexOf(formState.selectedExpenseCategory.expenseCategoryName)
                MenuSample(
                    menuWidth = 300,
                    selectedIndex = currentIndex,
                    menuItems = expenseCategories.map { it.expenseCategoryName },
                    onChangeSelectedIndex = {
                        val selectedExpenseCategory = expenseCategories[it]
                        onChangeExpenseCategory(selectedExpenseCategory)

                    }
                )
            }
            if (expenseCategories.isEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Enter an expense category to be add to an expense",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = MaterialTheme.colors.primary
                        )
                    )
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.onBackground
                ),
                onClick = {
                    keyBoard?.hide()
                    addExpense()
                }
            ) {
                Text(
                    text = "Save",
                    style = TextStyle(
                        color = MaterialTheme.colors.primary
                    )
                )

            }


        }
    }

}
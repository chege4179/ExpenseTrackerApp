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
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.components.MenuSample
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.AddTransactionScreenViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTransactionBottomSheet(
    navController: NavController,
    viewModel: AddTransactionScreenViewModel = hiltViewModel()
) {
    val keyBoard = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberScaffoldState()

    val transactionCategories = viewModel.transactionCategories
        .collectAsStateWithLifecycle(initialValue = emptyList())
        .value
        .map { it.toExternalModel() }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
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
            .height(500.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
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
                    text = "Create Transaction",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
            TextField(
                value = viewModel.transactionName.value,
                onValueChange = {
                    viewModel.onChangeTransactionName(text = it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Transaction Name",
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.primary
                )
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                TextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    value = getNumericInitialValue(viewModel.transactionAmount.value),
                    onValueChange = {
                        viewModel.onChangeTransactionAmount(text = it)
                    },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    placeholder = {
                        Text(
                            text = "Transaction Amount",
                            style = TextStyle(color = MaterialTheme.colors.primary)
                        )
                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.primary
                    )
                )
                Spacer(modifier = Modifier.size(16.dp))
                MenuSample(
                    menuWidth = 300,
                    selectedIndex = viewModel.selectedIndex.value,
                    menuItems = transactionCategories.map { it.transactionCategoryName },
                    onChangeSelectedIndex = {
                        viewModel.onChangeSelectedIndex(index = it)
                        val selectedTransactionCategory = transactionCategories[it]
                        viewModel.onChangeSelectedTransactionCategory(category = selectedTransactionCategory)
                    }
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        text = viewModel.transactionDate.value.toString(),
                        modifier = Modifier.fillMaxWidth(0.5f),
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )
                    Button(
                        modifier = Modifier
                            .width(width = 150.dp),
                        onClick = { dateDialogState.show() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.onBackground
                        )


                    ) {
                        Text(
                            text = "Pick A date",
                            style = TextStyle(color = MaterialTheme.colors.primary)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        text = viewModel.transactionTime.value.toString(),
                        modifier = Modifier.fillMaxWidth(0.5f),
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )
                    Button(
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.onBackground
                        ),
                        onClick = {
                            timeDialogState.show()

                        }
                    ) {
                        Text(
                            text = "Pick A Time",
                            style = TextStyle(color = MaterialTheme.colors.primary)
                        )
                    }
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
                    viewModel.addTransaction()
                }
            ) {
                Text(
                    text = "Save Transaction",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = MaterialTheme.colors.primary)
                )

            }
        }
        if (viewModel.isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "Pick") {
                    dateDialogState.hide()

                }
                negativeButton(text = "Cancel") {

                }
            }
        ) {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date",
            ) {
                viewModel.onChangeTransactionDate(date = it)

            }

        }
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(text = "Pick") {
                    timeDialogState.hide()

                }
                negativeButton(text = "Cancel") {

                }
            }
        ) {
            timepicker(
                initialTime = LocalTime.now(),
                title = "Pick a time",
            ) {
                viewModel.onChangeTransactionTime(time = it)

            }

        }
    }
}
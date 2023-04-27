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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.core.util.getNumericInitialValue
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.AddIncomeFormState
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.AddIncomeScreenViewModel
import com.peterchege.expensetrackerapp.presentation.components.MenuSample
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime

@Preview
@Composable
fun AddIncomeBottomSheetPreview(){
    val navController = rememberNavController()
    AddIncomeBottomSheetContent(
        eventFlow = MutableSharedFlow(),
        navController = navController,
        formState = AddIncomeFormState(),
        onChangeIncomeName = {  },
        onChangeIncomeAmount = {  },
        addIncome = {  }
    )
}

@Composable
fun AddIncomeBottomSheet(
    navController: NavController,
    viewModel: AddIncomeScreenViewModel = hiltViewModel(),
) {
    val formState = viewModel.formState.collectAsStateWithLifecycle()

    AddIncomeBottomSheetContent(
        eventFlow = viewModel.eventFlow,
        navController = navController,
        formState = formState.value,
        onChangeIncomeName = {
            viewModel.onChangeIncomeName(text = it)
        },
        onChangeIncomeAmount = {
            viewModel.onChangeIncomeAmount(text = it)
        },
        addIncome = {
            viewModel.addIncome()
        }
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddIncomeBottomSheetContent(
    eventFlow: SharedFlow<UiEvent>,
    navController: NavController,
    formState: AddIncomeFormState,
    onChangeIncomeName: (String) -> Unit,
    onChangeIncomeAmount: (String) -> Unit,
    addIncome: () -> Unit

) {
    val keyBoard = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberScaffoldState()

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
            .height(400.dp)
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
                    text = "Add Income",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
            TextField(
                value = formState.incomeName,
                onValueChange = {

                    onChangeIncomeName(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Income Name",
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.primary
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                value = getNumericInitialValue(formState.incomeAmount),
                onValueChange = {
                    onChangeIncomeAmount(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Income Amount",
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.primary
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.onBackground
                ),
                onClick = {
                    keyBoard?.hide()
                    addIncome()
                }
            ) {
                Text(
                    text = "Save Income",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = MaterialTheme.colors.primary)
                )

            }
        }
    }
}
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
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.AddExpenseCategoryFormState
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.AddExpenseCategoryScreenViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddExpenseCategoryBottomSheet(
    navController: NavController,
    viewModel: AddExpenseCategoryScreenViewModel = hiltViewModel()
) {
    val formState = viewModel.formState.collectAsStateWithLifecycle()

    AddExpenseCategoryBottomSheetContent(
        eventFlow = viewModel.eventFlow,
        formState = formState.value,
        onChangeExpenseCategoryName = {
            viewModel.onChangeExpenseName(it)
        },
        addExpenseCategory = { viewModel.addExpenseCategory() },
        navController = navController
    )

}


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddExpenseCategoryBottomSheetContent(
    eventFlow: SharedFlow<UiEvent>,
    formState: AddExpenseCategoryFormState,
    onChangeExpenseCategoryName: (String) -> Unit,
    addExpenseCategory: () -> Unit,
    navController: NavController,
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
            .height(250.dp)
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
                    text = "Create Expense Category",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
            TextField(
                value = formState.expenseCategoryName,
                onValueChange = {
                    onChangeExpenseCategoryName(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Expense Category Name",
                        style = TextStyle(color = MaterialTheme.colors.primary),
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.primary
                )
            )
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.onBackground
                ),
                onClick = {
                    keyBoard?.hide()
                    addExpenseCategory()

                }
            ) {
                Text(
                    text = "Save",
                    style = TextStyle(color = MaterialTheme.colors.primary),

                    )

            }
        }
        if (formState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
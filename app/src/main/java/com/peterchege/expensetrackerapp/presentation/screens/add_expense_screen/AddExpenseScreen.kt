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
package com.peterchege.expensetrackerapp.presentation.screens.add_expense_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.core.util.Mode
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.components.MenuSample
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddExpenseScreen(
    navController: NavController,
    viewModel: AddExpenseScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val expenseCategories = viewModel.expenseCategories
        .collectAsStateWithLifecycle(initialValue = emptyList())
        .value
        .map { it.toExternalModel() }



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
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier= Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text ="Create Expense ")

                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            if (viewModel.isLoading.value){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextField(
                    value = viewModel.expenseName.value,
                    onValueChange = {
                        viewModel.onChangeExpenseName(text = it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Expense Name")
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    value = viewModel.expenseAmount.value.toString(),
                    onValueChange = {
                        viewModel.onChangeExpenseAmount(text = it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Expense Amount")
                    },
                )
                Spacer(modifier = Modifier.size(16.dp))
                MenuSample(
                    menuWidth = 300,
                    selectedIndex = viewModel.selectedIndex.value,
                    menuItems = expenseCategories.map { it.expenseCategoryName },
                    onChangeSelectedIndex = {
                        viewModel.onChangeSelectedIndex(index = it)
                        val selectedExpenseCategory = expenseCategories[it]
                        viewModel.onChangeSelectedExpenseCategory(category = selectedExpenseCategory)

                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    onClick = {
                        viewModel.addExpense()
                    }
                ){
                    Text(text = "Save")

                }




            }
        }

    }
    
}
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
package com.peterchege.expensetrackerapp.presentation.screens.add_transaction_category_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.core.util.TestTags
import com.peterchege.expensetrackerapp.core.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTransactionCategoryScreen(
    navController: NavController,
    viewModel:AddTransactionCategoryScreenViewModel = hiltViewModel()
) {
    val keyBoard = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberScaffoldState()
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
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.onBackground,
                title = {
                    Text(
                        text ="Create Transaction Category",
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = viewModel.transactionCategoryName.value,
                    onValueChange = {
                        viewModel.onChangeTransactionCategoryName(text = it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(tag = TestTags.CREATE_TRANSACTION_CATEGORY_INPUT)

                    ,
                    placeholder = {
                        Text(
                            text = "Transaction Category Name",
                            style = TextStyle(color = MaterialTheme.colors.primary)
                        )
                    }
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(tag = TestTags.CREATE_TRANSACTION_CATEGORY_SAVE_BUTTON)
                    ,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.onBackground
                    ),
                    onClick = {
                        keyBoard?.hide()
                        viewModel.addTransactionCategory()

                    }
                ){
                    Text(
                        text = "Save",
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )

                }


            }
            if (viewModel.isLoading.value){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
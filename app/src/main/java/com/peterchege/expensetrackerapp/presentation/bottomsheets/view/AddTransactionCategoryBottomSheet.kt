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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.core.util.TestTags
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.AddTransactionCategoryFormState
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.AddTransactionCategoryScreenViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AddTransactionCategoryBottomSheet(
    navController: NavController,
    viewModel: AddTransactionCategoryScreenViewModel = hiltViewModel()
){

    val formState = viewModel.formState.collectAsStateWithLifecycle()

    AddTransactionCategoryBottomSheetContent(
        eventFlow = viewModel.eventFlow,
        navController = navController,
        formState = formState.value,
        onChangeTransactionCategoryName = { viewModel.onChangeTransactionCategoryName(it) },
        addTransactionCategory = { viewModel.addTransactionCategory() }
    )

}




@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTransactionCategoryBottomSheetContent(
    eventFlow: SharedFlow<UiEvent>,
    navController: NavController,
    formState:AddTransactionCategoryFormState,
    onChangeTransactionCategoryName:(String) -> Unit,
    addTransactionCategory:() -> Unit
) {
    val keyBoard = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ){
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onBackground)
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
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    text = "Create Transaction Category",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
            OutlinedTextField(
                value = formState.transactionCategoryName,
                onValueChange = {
                    onChangeTransactionCategoryName(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(tag = TestTags.CREATE_TRANSACTION_CATEGORY_INPUT)
                ,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                ),
                placeholder = {
                    Text(
                        text = "Transaction Category Name",
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )
                }
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(tag = TestTags.CREATE_TRANSACTION_CATEGORY_SAVE_BUTTON)
                ,
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = {
                    keyBoard?.hide()
                    addTransactionCategory()

                }
            ){
                Text(
                    text = "Save",
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )

            }


        }
        if (formState.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
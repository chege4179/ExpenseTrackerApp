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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.AddIncomeFormState
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.AddIncomeScreenViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onBackground)
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
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    text = "Add Income",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
            OutlinedTextField(
                value = formState.incomeName,
                onValueChange = {

                    onChangeIncomeName(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Income Name",
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
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
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = {
                    keyBoard?.hide()
                    addIncome()
                }
            ) {
                Text(
                    text = "Save Income",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )

            }
        }
    }
}
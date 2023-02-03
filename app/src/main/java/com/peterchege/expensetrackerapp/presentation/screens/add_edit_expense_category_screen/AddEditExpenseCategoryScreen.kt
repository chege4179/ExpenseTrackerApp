package com.peterchege.expensetrackerapp.presentation.screens.add_edit_expense_category_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.core.util.Mode
import com.peterchege.expensetrackerapp.core.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditExpenseCategoryScreen(
    navController: NavController,
    viewModel: AddEditExpenseCategoryScreenViewModel = hiltViewModel()
) {
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
                title = {
                    Text(text =
                    if (viewModel.mode.value == Mode.ADD_MODE)
                        "Create Expense Category"
                    else
                        "Edit Expense Category"
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.expenseCategoryName.value,
                onValueChange = {
                    viewModel.onChangeExpenseName(text = it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Expense Name")
                }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.addExpense()

                }
            ){
                Text(text = "Save")

            }


        }


    }
}
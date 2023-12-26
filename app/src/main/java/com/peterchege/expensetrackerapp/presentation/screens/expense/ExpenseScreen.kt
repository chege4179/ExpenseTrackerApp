package com.peterchege.expensetrackerapp.presentation.screens.expense

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.expensetrackerapp.presentation.components.ErrorComponent
import com.peterchege.expensetrackerapp.presentation.components.LoadingComponent

@Composable
fun ExpenseScreen(
    viewModel: ExpenseScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ExpenseScreenContent(uiState = uiState)
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ExpenseScreenContent(
    uiState: ExpenseScreenUiState,
    ) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                        text = "Expense",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    scrolledContainerColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.background,
                )
            )
        },
    ) { paddingValues ->
        when(uiState){
            is ExpenseScreenUiState.Loading -> {
                LoadingComponent()
            }
            is ExpenseScreenUiState.Error -> {
                ErrorComponent(message = uiState.message)
            }
            is ExpenseScreenUiState.Success -> {
                val transactionInfo = uiState.expenseInfo
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(paddingValues)
                        .padding(10.dp)
                ) {
                    transactionInfo.let {
                        it.expense?.let { expense ->
                            Text(
                                text = "Expense ID :" + expense.expenseId,
                                style = TextStyle(color = MaterialTheme.colorScheme.primary)

                            )
                            Text(
                                text = "Name :" + expense.expenseName,
                                style = TextStyle(color = MaterialTheme.colorScheme.primary)

                            )
                            Text(
                                text = "Amount :" + expense.expenseAmount,
                                style = TextStyle(color = MaterialTheme.colorScheme.primary)

                            )
                            Text(
                                text = "Created On :" + expense.expenseCreatedOn,
                                style = TextStyle(color = MaterialTheme.colorScheme.primary)
                            )
                            Text(
                                text = "Created At :" + expense.expenseCreatedAt,
                                style = TextStyle(color = MaterialTheme.colorScheme.primary)
                            )
                        }

                        it.expenseCategory?.let { category ->
                            Text(
                                text = "Category:" + category.expenseCategoryName,
                                style = TextStyle(color = MaterialTheme.colorScheme.primary)

                            )
                        }
                    }

                }
            }
        }

    }
}
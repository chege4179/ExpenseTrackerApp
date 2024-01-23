package com.peterchege.expensetrackerapp.presentation.screens.expense

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.expensetrackerapp.R
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.presentation.alertDialogs.ConfirmDeleteExpenseDialog
import com.peterchege.expensetrackerapp.presentation.components.ErrorComponent
import com.peterchege.expensetrackerapp.presentation.components.LoadingComponent

@Composable
fun ExpenseScreen(
    viewModel: ExpenseScreenViewModel = hiltViewModel(),
    navigateBack:() -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val deleteExpenseUiState by viewModel.deleteExpenseUiState.collectAsStateWithLifecycle()
    ExpenseScreenContent(
        uiState = uiState,
        deleteExpenseUiState = deleteExpenseUiState,
        setDeleteExpense = viewModel::setDeleteExpense,
        toggleDeleteExpenseDialog = viewModel::toggleDeleteExpenseDialogVisibility,
        deleteExpense = { viewModel.deleteExpense(navigateBack) }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ExpenseScreenContent(
    uiState: ExpenseScreenUiState,
    deleteExpenseUiState: DeleteExpenseUiState,
    setDeleteExpense: (Expense?) -> Unit,
    toggleDeleteExpenseDialog: () -> Unit,
    deleteExpense: () -> Unit,
) {

    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                        text = stringResource(id = R.string.expense),
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
        when (uiState) {
            is ExpenseScreenUiState.Loading -> {
                LoadingComponent()
            }

            is ExpenseScreenUiState.Error -> {
                ErrorComponent(message = uiState.message)
            }

            is ExpenseScreenUiState.Success -> {
                val transactionInfo = uiState.expenseInfo
                if (deleteExpenseUiState.isDeleteExpenseDialogVisible &&
                    deleteExpenseUiState.deleteExpense != null
                ) {
                    ConfirmDeleteExpenseDialog(
                        closeDeleteDialog = toggleDeleteExpenseDialog,
                        expense = deleteExpenseUiState.deleteExpense,
                        deleteExpense = deleteExpense
                    )
                }
                transactionInfo.let {
                    if (it.expense != null && it.expenseCategory != null){
                        val expense = it.expense
                        val category = it.expenseCategory
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.background)
                                .padding(paddingValues)
                                .padding(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
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
                                Text(
                                    text = "Category:" + category.expenseCategoryName,
                                    style = TextStyle(color = MaterialTheme.colorScheme.primary)

                                )

                            }
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    setDeleteExpense(it.expense)
                                },
                                colors = ButtonColors(
                                    contentColor = MaterialTheme.colorScheme.background,
                                    containerColor = MaterialTheme.colorScheme.onBackground,
                                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                                )
                            ) {
                                Text(
                                    text = stringResource(id = R.string.delete_expense),
                                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}
package com.peterchege.expensetrackerapp.presentation.alertDialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.peterchege.expensetrackerapp.R
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.models.Income

@Composable
fun ConfirmDeleteIncomeDialog(
    closeDeleteDialog:() -> Unit,
    income: Income,
    deleteIncome:() -> Unit,
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.onBackground,
        onDismissRequest = {
            closeDeleteDialog()
        },
        title = {
            Text(
                text = "${stringResource(id = R.string.delete)} '${income.incomeName}' ",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                )
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.delete_income_description),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    deleteIncome()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                    )
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    closeDeleteDialog()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                    )
                )
            }
        }
    )


}
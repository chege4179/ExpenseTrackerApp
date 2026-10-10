/*
 * Copyright 2026 Expense Tracker App By Peter Chege
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
package com.peterchege.expensetrackerapp.presentation.alertDialogs

import androidx.compose.ui.text.font.FontWeight
import com.peterchege.expensetrackerapp.presentation.preview.ThemePreviews
import com.peterchege.expensetrackerapp.presentation.preview.PreviewSurface
import com.peterchege.expensetrackerapp.presentation.preview.PreviewData
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.peterchege.expensetrackerapp.R
import com.peterchege.expensetrackerapp.domain.models.Transaction

@Composable
fun ConfirmDeleteTransactionDialog(
    closeDeleteDialog:() -> Unit,
    transaction: Transaction,
    deleteTransaction:() -> Unit,
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.onBackground,
        onDismissRequest = {
            closeDeleteDialog()
        },
        title = {
            Text(
                text = "${stringResource(id = R.string.delete)} '${transaction.transactionName}' ",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.delete_expense_description),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    deleteTransaction()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.SemiBold,
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

@ThemePreviews
@Composable
private fun ConfirmDeleteTransactionDialogPreview() {
    PreviewSurface {
        ConfirmDeleteTransactionDialog(
            closeDeleteDialog = {},
            transaction = PreviewData.transaction,
            deleteTransaction = {},
        )
    }
}

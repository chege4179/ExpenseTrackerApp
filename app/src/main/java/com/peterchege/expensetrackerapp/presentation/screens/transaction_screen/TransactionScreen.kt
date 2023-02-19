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
package com.peterchege.expensetrackerapp.presentation.screens.transaction_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TransactionScreen(
    navController: NavController,
    viewModel:TransactionScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.onBackground,
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        text = "Transaction",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            )
        },
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
                .padding(10.dp)
        ) {
            viewModel.transactionInfo.value?.let {
                it.transaction?.let { transaction ->
                    Text(
                        text = "Transaction ID :" + transaction.transactionId,
                        style = TextStyle(color = MaterialTheme.colors.primary)

                    )
                    Text(
                        text = "Name :" + transaction.transactionName,
                        style = TextStyle(color = MaterialTheme.colors.primary)

                    )
                    Text(
                        text = "Amount :" + transaction.transactionAmount,
                        style = TextStyle(color = MaterialTheme.colors.primary)

                    )
                    Text(
                        text = "Created On :" + transaction.transactionCreatedOn,
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )
                    Text(
                        text = "Created At :" + transaction.transactionCreatedAt,
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )
                }

                it.category?.let { category ->
                    Text(
                        text = "Category:" + category.transactionCategoryName,
                        style = TextStyle(color = MaterialTheme.colors.primary)

                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.onBackground
                    ),
                    onClick = {
                        viewModel.deleteTransaction()
                        Toast.makeText(context, "Transaction deleted",Toast.LENGTH_SHORT).show()

                    }
                ){
                    Text(
                        text = "Delete Transaction",
                        style = TextStyle(color = MaterialTheme.colors.primary),

                        )

                }
            }

        }
    }
}
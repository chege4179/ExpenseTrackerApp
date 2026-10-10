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
package com.peterchege.expensetrackerapp.presentation.navigation

import androidx.navigation.NavHostController
import com.peterchege.expensetrackerapp.core.util.Screens


fun NavHostController.navigateToTransactionScreen(transactionId:String){
    navigate(route = Screens.TRANSACTIONS_SCREEN + "/$transactionId")
}

fun NavHostController.navigateToAllTransactionsScreen(){
    navigate(route = Screens.ALL_TRANSACTIONS_SCREEN)
}

fun NavHostController.navigateToAllIncomeScreen(){
    navigate(Screens.ALL_INCOME_SCREEN)
}

fun NavHostController.navigateToExpenseScreen(expenseId:String){
    navigate(Screens.EXPENSE_SCREEN + "/$expenseId")
}

fun NavHostController.navigateToAllExpenseScreen(){
    navigate(Screens.ALL_EXPENSES_SCREEN)
}

fun NavHostController.navigateToAboutScreen(){
    navigate(Screens.ABOUT_SCREEN)
}
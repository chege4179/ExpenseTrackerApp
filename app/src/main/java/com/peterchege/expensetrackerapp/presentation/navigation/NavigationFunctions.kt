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
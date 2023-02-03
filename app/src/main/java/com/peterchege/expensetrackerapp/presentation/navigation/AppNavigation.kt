package com.peterchege.expensetrackerapp.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.presentation.screens.add_edit_expense_category_screen.AddEditExpenseCategoryScreen
import com.peterchege.expensetrackerapp.presentation.screens.add_edit_expense_screen.AddEditExpenseScreen
import com.peterchege.expensetrackerapp.presentation.screens.add_edit_transaction_category_screen.AddEditTransactionCategoryScreen
import com.peterchege.expensetrackerapp.presentation.screens.add_edit_transaction_screen.AddEditTransactionScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppNavigation(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.BOTTOM_TAB_NAVIGATION
    ){

        composable(route = Screens.BOTTOM_TAB_NAVIGATION){
            BottomNavigationWrapper(navHostController = navHostController)
        }
        composable(route = Screens.ADD_EDIT_EXPENSE_SCREEN + "/{mode}"){
            AddEditExpenseScreen(navController = navHostController)
        }
        composable(route = Screens.ADD_EDIT_TRANSACTION_SCREEN  +"/{mode}"){
            AddEditTransactionScreen(navController = navHostController)
        }
        composable(route = Screens.ADD_EDIT_EXPENSE_CATEGORY_SCREEN +"/{mode}"){
            AddEditExpenseCategoryScreen(navController = navHostController)
        }

        composable(route = Screens.ADD_EDIT_TRANSACTION_CATEGORY_SCREEN +"/{mode}"){
            AddEditTransactionCategoryScreen(navController = navHostController)
        }

    }

}
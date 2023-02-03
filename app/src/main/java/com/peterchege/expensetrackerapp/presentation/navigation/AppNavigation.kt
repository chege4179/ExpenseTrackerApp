package com.peterchege.expensetrackerapp.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.presentation.screens.add_edit_category_screen.AddEditCategoryScreen
import com.peterchege.expensetrackerapp.presentation.screens.add_edit_expense_screen.AddEditExpenseScreen
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
        composable(route = Screens.ADD_EDIT_EXPENSE_SCREEN){
            AddEditExpenseScreen(navController = navHostController)
        }
        composable(route = Screens.ADD_EDIT_CATEGORY_SCREEN){
            AddEditCategoryScreen(navController = navHostController)
        }
        composable(route = Screens.ADD_EDIT_TRANSACTION_SCREEN){
            AddEditTransactionScreen(navController = navHostController)
        }

    }

}
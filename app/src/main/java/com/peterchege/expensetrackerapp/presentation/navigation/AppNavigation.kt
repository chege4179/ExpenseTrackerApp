package com.peterchege.expensetrackerapp.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.presentation.screens.add_expense_category_screen.AddExpenseCategoryScreen
import com.peterchege.expensetrackerapp.presentation.screens.add_expense_screen.AddExpenseScreen
import com.peterchege.expensetrackerapp.presentation.screens.add_transaction_category_screen.AddTransactionCategoryScreen
import com.peterchege.expensetrackerapp.presentation.screens.add_transaction_screen.AddTransactionScreen

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
        composable(route = Screens.ADD_EXPENSE_SCREEN ){
            AddExpenseScreen(navController = navHostController)
        }
        composable(route = Screens.ADD_TRANSACTION_SCREEN  ){
            AddTransactionScreen(navController = navHostController)
        }
        composable(route = Screens.ADD_EXPENSE_CATEGORY_SCREEN ){
            AddExpenseCategoryScreen(navController = navHostController)
        }

        composable(route = Screens.ADD_TRANSACTION_CATEGORY_SCREEN ){
            AddTransactionCategoryScreen(navController = navHostController)
        }

    }

}
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
package com.peterchege.expensetrackerapp.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.presentation.screens.all_transactions_screen.AllTransactionsScreen
import com.peterchege.expensetrackerapp.presentation.screens.transaction_screen.TransactionScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppNavigation(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.BOTTOM_TAB_NAVIGATION
    ) {

        composable(route = Screens.BOTTOM_TAB_NAVIGATION) {
            BottomNavigationWrapper(navHostController = navHostController)
        }
        composable(route = Screens.TRANSACTIONS_SCREEN + "/{id}") {
            TransactionScreen(navController = navHostController)
        }
        composable(route = Screens.ALL_TRANSACTIONS_SCREEN) {
            AllTransactionsScreen(navController = navHostController)
        }

    }

}
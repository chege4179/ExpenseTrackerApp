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

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.core.util.TrackDisposableJank
import com.peterchege.expensetrackerapp.presentation.screens.about.AboutScreen
import com.peterchege.expensetrackerapp.presentation.screens.expense.ExpenseScreen
import com.peterchege.expensetrackerapp.presentation.screens.expenses.AllExpensesScreen
import com.peterchege.expensetrackerapp.presentation.screens.income.AllIncomeScreen
import com.peterchege.expensetrackerapp.presentation.screens.onboarding.OnboardingScreen
import com.peterchege.expensetrackerapp.presentation.screens.transactions.AllTransactionsScreen
import com.peterchege.expensetrackerapp.presentation.screens.transaction.TransactionScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun AppNavigation(
    navHostController: NavHostController,
    shouldShowOnBoarding: Boolean,
    openOSSMenu: () -> Unit,
) {
    NavigationTrackingSideEffect(navController = navHostController)
    NavHost(
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        navController = navHostController,
        startDestination = if (shouldShowOnBoarding) Screens.ONBOARDING_SCREEN else Screens.BOTTOM_TAB_NAVIGATION
    ) {
        val enterAnimation = scaleInEnterTransition()
        val exitAnimation = scaleOutExitTransition()
        val popEnterAnimation = scaleInPopEnterTransition()
        val popExitAnimation = scaleOutPopExitTransition()
        composable(Screens.ONBOARDING_SCREEN) {
            OnboardingScreen(
                navigateHome = {
                    navHostController.navigate(Screens.BOTTOM_TAB_NAVIGATION) {

                    }
                }
            )
        }
        composable(route = Screens.BOTTOM_TAB_NAVIGATION) {
            BottomNavigationWrapper(
                navHostController = navHostController,
                openOSSMenu = openOSSMenu
            )
        }
        composable(
            route = Screens.TRANSACTIONS_SCREEN + "/{id}",
            enterTransition = { enterAnimation },
            exitTransition = { exitAnimation },
            popEnterTransition = { popEnterAnimation },
            popExitTransition = { popExitAnimation }

        ) {
            TransactionScreen(navigateBack = { navHostController.navigate(Screens.BOTTOM_TAB_NAVIGATION) })
        }
        composable(
            route = Screens.ALL_TRANSACTIONS_SCREEN,
            enterTransition = { enterAnimation },
            exitTransition = { exitAnimation },
            popEnterTransition = { popEnterAnimation },
            popExitTransition = { popExitAnimation }
        ) {
            AllTransactionsScreen(navigateToTransactionScreen = navHostController::navigateToTransactionScreen)
        }

        composable(
            route = Screens.ALL_EXPENSES_SCREEN,
            enterTransition = { enterAnimation },
            exitTransition = { exitAnimation },
            popEnterTransition = { popEnterAnimation },
            popExitTransition = { popExitAnimation }
        ) {
            AllExpensesScreen(
                navigateToExpenseScreen = navHostController::navigateToExpenseScreen
            )
        }
        composable(
            route = Screens.EXPENSE_SCREEN + "/{id}",
            enterTransition = { enterAnimation },
            exitTransition = { exitAnimation },
            popEnterTransition = { popEnterAnimation },
            popExitTransition = { popExitAnimation }
        ) {
            ExpenseScreen(navigateBack = { navHostController.navigate(Screens.BOTTOM_TAB_NAVIGATION) })
        }

        composable(
            route = Screens.ABOUT_SCREEN,
            enterTransition = { enterAnimation },
            exitTransition = { exitAnimation },
            popEnterTransition = { popEnterAnimation },
            popExitTransition = { popExitAnimation }
        ) {
            AboutScreen()
        }

        composable(
            route = Screens.ALL_INCOME_SCREEN,
            enterTransition = { enterAnimation },
            exitTransition = { exitAnimation },
            popEnterTransition = { popEnterAnimation },
            popExitTransition = { popExitAnimation }
        ) {
            AllIncomeScreen(navController = navHostController)
        }

    }

}

@Composable
fun NavigationTrackingSideEffect(navController: NavHostController) {
    TrackDisposableJank(navController) { metricsHolder ->
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            metricsHolder.state?.putState("Navigation", destination.route.toString())
        }
        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
}

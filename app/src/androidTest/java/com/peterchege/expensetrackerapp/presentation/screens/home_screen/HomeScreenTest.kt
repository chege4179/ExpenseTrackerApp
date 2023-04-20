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
package com.peterchege.expensetrackerapp.presentation.screens.home_screen

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.presentation.MainActivity
import com.peterchege.expensetrackerapp.presentation.navigation.BottomNavigationWrapper
import com.peterchege.expensetrackerapp.presentation.screens.transaction_screen.TransactionScreen
import com.peterchege.expensetrackerapp.presentation.theme.ExpenseTrackerAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule


@HiltAndroidTest
@UninstallModules(AppModule::class)
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @OptIn(ExperimentalMaterialApi::class)
    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navHostController = rememberNavController()
            ExpenseTrackerAppTheme {
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
                }
            }
        }
    }
}
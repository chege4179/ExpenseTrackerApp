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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.domain.models.BottomNavItem
import com.peterchege.expensetrackerapp.presentation.screens.analytics.AnalyticsScreen
import com.peterchege.expensetrackerapp.presentation.screens.home.HomeScreen
import com.peterchege.expensetrackerapp.presentation.screens.search.SearchScreen
import com.peterchege.expensetrackerapp.presentation.screens.settings.SettingsScreen


@ExperimentalMaterial3Api
@Composable
fun BottomNavigationWrapper(
    navHostController: NavHostController,
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                items = listOf(
                    BottomNavItem(
                        name="Home",
                        route = Screens.HOME_SCREEN  ,
                        icon = Icons.Default.Home
                    ),
                    BottomNavItem(
                        name="Search",
                        route = Screens.SEARCH_SCREEN   ,
                        icon = Icons.Default.Search
                    ),
                    BottomNavItem(
                        name="Analytics",
                        route = Screens.ANALYTICS_SCREEN   ,
                        icon = Icons.Outlined.BarChart
                    ),
                    BottomNavItem(
                        name="Settings",
                        route = Screens.SETTINGS_SCREEN   ,
                        icon = Icons.Default.Settings
                    ),
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route){
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier
            .background(Color.LightGray)
            .padding(innerPadding)
        ) {
            BottomNavigation(
                navController = navController,
                navHostController = navHostController
            )
        }

    }
}

@ExperimentalMaterial3Api
@Composable
fun BottomNavBar(
    items:List<BottomNavItem>,
    navController: NavController,
    onItemClick:(BottomNavItem) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.onBackground,
    ) {
        items.forEachIndexed { index, item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name
                    )
                },
                label = { Text(text = item.name) },
                selected = selected,
                onClick = { onItemClick(item) }
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomNavigation(
    navController: NavHostController,
    navHostController: NavHostController
) {
    NavHost(

        navController = navController,
        startDestination = Screens.HOME_SCREEN
    ){

        composable(
            route = Screens.HOME_SCREEN
        ){
            HomeScreen(
                navigateToAllExpensesScreen = navHostController::navigateToAllExpenseScreen,
                navigateToAllIncomeScreen = navHostController::navigateToAllIncomeScreen,
                navigateToAllTransactionsScreen = navHostController::navigateToAllTransactionsScreen,
                navigateToTransactionScreen = navHostController::navigateToTransactionScreen
            )
        }
        composable(
            route = Screens.SEARCH_SCREEN
        ){
            SearchScreen(
                navigateToTransactionScreen = navHostController::navigateToTransactionScreen
            )
        }
        composable(
            route = Screens.ANALYTICS_SCREEN
        ){
            AnalyticsScreen()
        }
        composable(
            route = Screens.SETTINGS_SCREEN
        ){
            SettingsScreen()
        }


    }

}
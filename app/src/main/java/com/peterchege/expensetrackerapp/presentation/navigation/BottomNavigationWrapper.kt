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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.domain.models.BottomNavItem
import com.peterchege.expensetrackerapp.presentation.screens.analytics_screen.AnalyticsScreen
import com.peterchege.expensetrackerapp.presentation.screens.home_screen.HomeScreen
import com.peterchege.expensetrackerapp.presentation.screens.search_screen.SearchScreen
import com.peterchege.expensetrackerapp.presentation.screens.settings_screen.SettingsScreen
import com.peterchege.expensetrackerapp.presentation.theme.BlueColor


@ExperimentalMaterialApi
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
                onItemClick ={
                    navController.navigate(it.route)
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

@ExperimentalMaterialApi
@Composable
fun BottomNavBar(
    items:List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick:(BottomNavItem) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name
                        )
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp

                            )
                        }

                    }

                }
            )
        }


    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigation(
    navController: NavHostController,
    navHostController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.HOME_SCREEN){

        composable(
            route = Screens.HOME_SCREEN
        ){
            HomeScreen(navController = navHostController)
        }
        composable(
            route = Screens.SEARCH_SCREEN
        ){
            SearchScreen(navController = navHostController)
        }
        composable(
            route = Screens.ANALYTICS_SCREEN
        ){
            AnalyticsScreen(navController = navController)
        }
        composable(
            route = Screens.SETTINGS_SCREEN
        ){
            SettingsScreen(navController = navController)
        }


    }

}